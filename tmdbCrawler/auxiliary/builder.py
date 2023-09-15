from tmdbCrawler.auxiliary.login import Login
from tmdbCrawler.auxiliary.tmdb import TMDB


class CrawlerBuilder:
    __account: str
    __password: str
    __searchName: str
    __tmdb: TMDB
    __searchLimit = 9
    __actorLimit = 3
    __hide = True

    def setAccount(self, account: str):
        self.__account = account
        return self

    def setPassword(self, password: str):
        self.__password = password
        return self

    def setSearchLimit(self, limit: int):
        self.__searchLimit = limit
        return self

    def setActorLimit(self, limit: int):
        self.__actorLimit = limit
        return self

    def setWhetherHideBrowser(self, hide: bool):
        self.__hide = hide
        return self

    def setTMDB(self, searchName: str):
        self.__searchName = searchName
        return self

    def build(self):
        if self.__account is None or self.__password is None:
            print('need账号密码为空要我爬到哪里去')
            return self
        if self.__tmdb is None:
            if self.__searchName is None:
                print('你这让我爬什么')
                return self
            else:
                self.__tmdb = TMDB(self.__searchName, self.__searchLimit, self.__actorLimit)
        Login(self.__account, self.__password, self.__tmdb, self.__hide)
        return
