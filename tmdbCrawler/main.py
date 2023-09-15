from tmdbCrawler.auxiliary.login import Login
from tmdbCrawler.auxiliary.tmdb import TMDB
from tmdbCrawler.auxiliary.builder import CrawlerBuilder

# Python鼓励开放的访问模型，强调“大家都是成年人”的思想，所以类中不需要有私有的
# md就是懒得写，就那俩下划线够谁看
# Login(账号，密码，tmdb，是否后台运行)
# 对了，爬虫结束后会登出，如果你多账户登录会显示未登录(认证)，因为redis内存中已经删除了你的token
# 所以不建议用你正在用的账号作为爬虫的账号！！！
# TMDB(搜索内容，爬取的番剧数量，爬取的职工数量)
# 你搜索的内容将被装载为ip，请你谨慎搜索
# 只有构造函数和get()函数是有用的，构造函数会调用所有的方法了
# 构造函数最后返回一个作品列表，只需要调用get()方法就可以获取到这个作品列表了
# 还有，那个auxiliary包里的那个txt文件不要乱动噢，那个是负责记录下载了什么，避免重复下载
if __name__ == '__main__':
    # 示例，账号      密码            搜索的ip    只获取前3个作品     只获取一排职工(8个，配音演员什么的)
    Login('123123', '123123', TMDB('复仇者联盟', 3,                1)) # 最后一个默认参数，后台运行
    # 还有建造者模式可以用，我建议这么用好一点，可观性强
    CrawlerBuilder()\
        .setAccount('123123')\
        .setPassword('123123')\
        .setSearchName('复仇者联盟')\
        .setSearchLimit(3)\
        .setActorLimit(1)\
        .build()      # 最后记得要调用这个，这个是真正调用了登录并且爬取进去的方法，爬取失败会返回当前对象


