class Celebrity:
    name = ''
    title = ''
    url = ''

    def __init__(self, name, title, url):
        self.name = name
        self.url = url
        self.title = title

    def __str__(self):
        return (f'''\
职工名字：{self.name}    
职工头像链接：{self.url}
职工职位：{self.title}
        ''')


class Artwork:
    artworkName = ''
    avatarUrl = ''
    addressUrl = ''
    ip = ''
    publishDate = ''
    introduce = ''
    type = ''
    celebrities: list[Celebrity]

    def __init__(self):
        pass

    def __str__(self):
        celebritiesString = ''
        for celebrity in self.celebrities:
            celebritiesString += celebrity.__str__()

        return (f'''\
作品名：{self.artworkName}
作品图片地址：{self.avatarUrl}
作品链接：{self.addressUrl}
作品类型：{self.type}
作品简介：{self.introduce}
作品ip：{self.ip}
作品上映时间：{self.publishDate}
作品演职员：{celebritiesString}
        ''')