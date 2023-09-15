import requests
from tmdbCrawler.auxiliary.artwork import Artwork, Celebrity
from lxml import etree


class TMDB:
    artworks: list[Artwork] = []
    search: str
    searchLimit: int
    actorLimit: int
    hasUpload: set[str]
    headers = {
        'User-Agent':
            'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36 Edg/115.0.1901.200',
        'Accept-Language':
            'zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6'
    }
    defaultUrl = 'https://www.themoviedb.org'

    # 你搜索的内容将被装载为ip，请你谨慎搜索
    # 只有构造函数和get()函数是有用的，构造函数会调用所有的方法了
    # 最后返回一个作品列表，只需要调用get()方法就可以获取到这个作品列表了
    def __init__(self, search: str, searchLimit=9, actorLimit=3):
        self.searchLimit = searchLimit
        self.actorLimit = actorLimit
        self.search = search
        with open('uploadNames.txt', 'r', encoding='utf-8') as f:
            self.hasUpload = set(f.read().split('\n'))
        searchUrl = f'https://www.themoviedb.org/search?query={search}'
        response = requests.get(searchUrl, headers=self.headers)
        if response.ok:
            self.findArtworks(response.text)
        else:
            print('抓取失败')

    def get(self) -> list[Artwork]:
        return self.artworks

    def findArtworks(self, text):
        sourceXpath = '//div[@class="card v4 tight"]/div/div/div/a/@href'
        html = etree.HTML(text)
        target: list = html.xpath(sourceXpath)
        print(f'有{len(target)}条电影被找到')
        for index in range(min(self.searchLimit, len(target))):
            url = self.defaultUrl + target[index]
            self.getDatas(url)

    def getDatas(self, url) -> None:
        artwork = Artwork()
        response = requests.get(url, headers=self.headers)
        if response.ok:
            artwork.artworkName = self.getArtworkName(response.text)
            if artwork.artworkName == '':
                print('这个番剧已经加载过了，不再加载')
                return
            artwork.addressUrl = url
            artwork.avatarUrl = self.getAvatarUrl(response.text)
            artwork.introduce = self.getIntroduce(response.text)
            artwork.type = '番剧'
            artwork.ip = self.search
            artwork.publishDate = self.getPublishDate(response.text)
            artwork.celebrities = self.getCelebrities(response.text)
            self.artworks.append(artwork)
        else:
            print('爬取失败')

# 往后都是获取单一数据的方法了
    def getArtworkName(self, text) -> str:
        xpath = '//h2[1]'
        html = etree.HTML(text)
        s = xpath + '/a/text()'
        firstName: list[str] = html.xpath(s)
        secondName: list[str] = html.xpath(xpath + '/span/text()')
        name = firstName[0] + secondName[0]
        if self.hasUpload.intersection({name}):
            # 重复的了，那就不加载了
            return ''
        else:
            return name

    def getAvatarUrl(self, text) -> str:
        xpath = '//div[@class="zoom"]/preceding-sibling::div/img/@data-src'
        html = etree.HTML(text)
        src: list[str] = html.xpath(xpath)
        return self.defaultUrl + src[0]

    def getIntroduce(self, text) -> str:
        xpath = '//*[@id="original_header"]/div[2]/section/div[2]/div/p/text()'
        html = etree.HTML(text)
        intro: list[str] = html.xpath(xpath)
        return intro[0].strip('"')

    def getPublishDate(self, text) -> str:
        xpath = '//span[@class="release"]/text()'
        html = etree.HTML(text)
        date: list[str] = html.xpath(xpath)
        return date[0].strip('"').strip().split(' ')[0]

    def getCelebrities(self, text) -> list[Celebrity]:
        celebrities: list[Celebrity] = []
        xpath = '//a[text()="完整演职员表"]/@href'
        html = etree.HTML(text)
        url = self.defaultUrl + html.xpath(xpath)[0]
        response = requests.get(url, headers=self.headers)
        if response.ok:
            celebrities.extend(self.getActors(response.text))
            celebrities.extend(self.getArt(response.text))
            celebrities.extend(self.getDirectors(response.text))
            celebrities.extend(self.getCasting(response.text))
            celebrities.extend(self.getSound(response.text))
            celebrities.extend(self.getEditor(response.text))
            celebrities.extend(self.getAnimation(response.text))
            celebrities.extend(self.getCharacters(response.text))
            return celebrities
        else:
            print('抓取失败')

    # 获取演员，配音演员也变成了演员，这个要区分开
    def getActors(self, text) -> list[Celebrity]:
        celebrities: list[Celebrity] = []
        xpath = '//h3[text()="演员 "]/following-sibling::ol/li'
        html = etree.HTML(text)
        actors = html.xpath(xpath)
        for index in range(min(self.actorLimit, len(actors))):
            imageSecondUrl = '/a/img/@src'
            imageUrl = self.defaultUrl + html.xpath(xpath + imageSecondUrl)[index]
            nameUrl = '/div/div/p/a/text()'
            name = html.xpath(xpath + nameUrl)[index]
            # 要判断是否是配音演员还是演员，这个有区别的
            voiceOrActor: str = html.xpath(xpath + '/div/div/p[3]/text()')[0]
            judgeWhetherVoice = voiceOrActor.find('voice')
            if judgeWhetherVoice == -1:
                celebrities.append(Celebrity(name, '演员', imageUrl))
            else:
                celebrities.append(Celebrity(name, '配音演员', imageUrl))
        return celebrities

    # 演员的和这些工作人员的xpath获取不一样
    def getWorkersByTitle(self, text, title) -> list[Celebrity]:
        celebrities: list[Celebrity] = []
        xpath = f'//h4[text()="{title}"]/following-sibling::ol/li'
        html = etree.HTML(text)
        workers = html.xpath(xpath)
        for index in range(min(self.actorLimit, len(workers))):
            imageSecondUrl = '/a/img/@src'
            imageTarget: list[str] = html.xpath(xpath + imageSecondUrl)
            if len(imageTarget) == 0:
                return celebrities
            imageUrl = self.defaultUrl + imageTarget[index]
            nameUrl = '/div/div/span/p/a/text()'
            name = html.xpath(xpath + nameUrl)[index]
            celebrities.append(Celebrity(name, title, imageUrl))
        return celebrities

    # 获取艺术
    def getArt(self, text) -> list[Celebrity]:
        return self.getWorkersByTitle(text, '艺术')

    # 获取导演
    def getDirectors(self, text) -> list[Celebrity]:
        return self.getWorkersByTitle(text, '导演')

    # 获取制片
    def getCasting(self, text) -> list[Celebrity]:
        return self.getWorkersByTitle(text, '制片')

    # 获取录音
    def getSound(self, text) -> list[Celebrity]:
        return self.getWorkersByTitle(text, '录音')

    # 获取剪辑
    def getEditor(self, text) -> list[Celebrity]:
        return self.getWorkersByTitle(text, '剪辑')

    # 获取视效
    def getAnimation(self, text) -> list[Celebrity]:
        return self.getWorkersByTitle(text, '视效')

    # 获取编剧
    def getCharacters(self, text) -> list[Celebrity]:
        return self.getWorkersByTitle(text, '编剧')
