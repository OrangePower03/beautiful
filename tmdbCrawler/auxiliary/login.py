from selenium import webdriver
from selenium.webdriver import Keys
from selenium.webdriver.common.by import By
from time import sleep

from selenium.webdriver.remote.webelement import WebElement

from tmdb import TMDB
from tmdbCrawler.auxiliary.artwork import Artwork, Celebrity


class Login:
    driver: webdriver
    artworks: list[Artwork]
    index = 0

    def __init__(self, account: str, password: str, tmdb: TMDB, hide=True):
        self.artworks = tmdb.get()
        url = 'http://localhost:5173/login'
        if hide:
            options = webdriver.ChromeOptions()
            options.add_argument('--headless')  # 启用无头模式
            options.add_argument('--disable-gpu')  # 禁用GPU加速，有些系统需要加上这个选项
            self.driver = webdriver.Chrome(options=options)
        else:
            self.driver = webdriver.Chrome()
            self.driver.maximize_window()
        self.driver.get(url)
        self.driver.find_element(By.XPATH, '//input[@placeholder="账号"]').send_keys(account)
        self.driver.find_element(By.XPATH, '//input[@placeholder="密码"]').send_keys(password)
        self.driver.find_element(By.XPATH, '//input[@value="登入"]').click()
        sleep(2)
        self.doUpload()
        # self.test().addCelebrities()

    def doUpload(self):
        with open('uploadNames.txt', 'a', encoding='utf-8') as f:
            for index in range(len(self.artworks)):
                self.driver.find_element(By.XPATH, '//span[text()="上传"]').click()
                sleep(1)
                self.inputName_Avatar_Address()
                self.ip()
                self.date()
                self.introduce()
                self.type()
                self.addCelebrities()
                self.index += 1
                sleep(0.5)
                # 点击上传按钮操作
                self.driver.find_element(By.XPATH, '//button[@type="submit"]').click()
                f.write(self.artworks[index].artworkName + '\n')
                sleep(0.5)
        self.driver.find_element(By.XPATH, '//span[text()="登出"]').click()
        self.driver.quit()

    def inputName_Avatar_Address(self):
        xpath = '//input[@class="ant-input css-dev-only-do-not-override-12upa3x"]'
        elements: list[WebElement] = self.driver.find_elements(By.XPATH, xpath)
        elements[0].send_keys(self.artworks[self.index].artworkName)
        elements[1].send_keys(self.artworks[self.index].avatarUrl)
        elements[2].send_keys(self.artworks[self.index].addressUrl)

    def ip(self):
        xpath = '//input[@id="basic_ip"]'
        self.driver.find_element(By.XPATH, xpath) \
            .send_keys(self.artworks[self.index].ip)

    def date(self):
        xpath = '//input[@id="basic_date"]'
        element = self.driver.find_element(By.XPATH, xpath)
        element.send_keys(self.artworks[self.index].publishDate)
        sleep(1)
        element.send_keys(Keys.ENTER)

    def introduce(self):
        xpath = '//textarea[@id="basic_intro"]'
        self.driver.find_element(By.XPATH, xpath) \
            .send_keys(self.artworks[self.index].introduce)

    def type(self):
        xpath = '//input[@id="basic_kind"]'
        element = self.driver.find_element(By.XPATH, xpath)
        element.click()
        sleep(0.2)
        element.send_keys(Keys.ENTER)

    def addCelebrities(self):
        i = 0
        celebrities: list[Celebrity] = self.artworks[self.index].celebrities
        for celebrity in celebrities:
            xpath = '//span[text()="新增演职人员"]'
            self.driver.find_element(By.XPATH, xpath).click()
            sleep(0.2)
            nameXpath = f'//input[@id="basic_celebrities_{i}_name"]'
            self.driver.find_element(By.XPATH, nameXpath) \
                .send_keys(celebrity.name)
            urlXpath = f'//input[@id="basic_celebrities_{i}_avatar"]'
            self.driver.find_element(By.XPATH, urlXpath) \
                .send_keys(celebrity.url)
            titleXpath = f'//input[@id="basic_celebrities_{i}_title"]'
            self.driver.find_element(By.XPATH, titleXpath).click()
            sleep(0.2)

            optionXpath = f'//div[@class="rc-virtual-list-holder-inner"]/div[@title="{celebrity.title}"]'
            e = self.driver.find_elements(By.XPATH, optionXpath)[i]
            # 滚到看得见的地方才那个啥
            self.driver.execute_script("arguments[0].scrollIntoView();", e)
            e.click()
            i += 1

    def test(self):
        self.driver.find_element(By.XPATH, '//span[text()="上传"]').click()
        sleep(2)
        return self
