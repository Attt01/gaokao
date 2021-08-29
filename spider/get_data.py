from bs4 import BeautifulSoup     #网页解析，获取数据
import re       #正则表达式，进行文字匹配
import urllib.request,urllib.error      #制定URL，获取网页数据
import xlwt     #进行excel操作
import sqlite3  #进行SQLite数据库操作
import time
from selenium import webdriver
from selenium.webdriver import ActionChains
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.support.ui import Select
from selenium.webdriver.common.keys import Keys
from urllib import parse
import  random

def main():
    chrome_options = Options()
    chrome_options.add_argument('--headless')
    chrome_options.add_argument('--disable-gpu')
    chrome_options.add_experimental_option('excludeSwitches', ['enable_automation'])
    driver = webdriver.Chrome(chrome_options=chrome_options)
    f = open("d://school_names.txt","r")
    error_f = open("d://error","a")
    line = f.readline()
    c = 1
    while line:
        try:
            baseurl = get_baseurl(line)
            datalist = []
            get_page_pre(driver,baseurl,datalist)#爬取概览页内容
            #借用上一个页面
            driver.find_element_by_xpath("/html/body/div/article/section/div[1]/div[1]/div/div[2]/div/div[2]/div[2]/div[1]/ul/li[1]/div/div[1]/a").click()
            time.sleep(0.5)
            set_province_first(driver)
            set_province_second(driver)
            set_year_first(driver)
            set_year_second(driver)
            flag1 = set_type_first(driver,"普通类一段")
            if flag1 == True:
                get_page_score(driver,datalist)
            else:
                datalist.append("")#13
                datalist.append("")#14
                datalist.append("")#15
            flag2 = set_type_first(driver,"普通类二段")
            if flag2 == True:#False直接赋为空
                get_page_score(driver,datalist)
            else:
                datalist.append("")#16
                datalist.append("")#17
                datalist.append("")#18
            #爬取招生页
            driver.find_element_by_xpath("/html/body/div[2]/div[2]/div[2]/div/ul/li[4]").click()
            time.sleep(0.5)
            set_province_ad(driver)
            set_year_ad(driver)
            if flag1 == True:
                set_type_ad(driver,"普通类一段")
                get_page_ad(driver,datalist)
            else:
                datalist.append("")#19
            if flag2 == True:
                set_type_ad(driver,"普通类二段")
                get_page_ad(driver,datalist)
            else:
                datalist.append("")#120
            #爬取专业页
            driver.find_element_by_xpath("/html/body/div[2]/div[2]/div[2]/div/ul/li[3]").click()
            time.sleep(0.1)
            get_page_special(driver,datalist)
            #整合数据
            try:
                integration(datalist)
            except:
                print("error")
            savedata(datalist[0],datalist)
            print(datalist[0])
            print(c)
            c += 1
            close_driver(driver)
        except:
            error_f.write(str(line)+'\n')
            error_f.close()
            error_f = open("d://error","a")
        line = f.readline()
    error_f.close()
    f.close()

def savedata(school_name,datatlist):
    # savepath = "d://kuake//school2//"+str(school_name)+".xls"
    savepath = "d://kuake_数据//school/school - 副本（2）//"+str(school_name)+".xls"
    book = xlwt.Workbook(encoding="utf-8", style_compression=0)
    sheet = book.add_sheet('院校信息', cell_overwrite_ok=True)
    col = ('名称','学校代码','学校类型','省份','校徽链接','标签1','标签2','简介','地址','就业率','出国率','考研率','一段最低分','一段最低位次','专业名称','选考要求','最低分','最低位次','录取人数','学制','学费','二段最低分','二段最低位次','专业名称','选考要求','最低分','最低位次','录取人数','学制','学费','双一流','国家特色','硕士点','博士点')#33
    for j in range(len(col)):
        sheet.write(0,j,col[j])
    for j in range(14):
        sheet.write(1,j,datatlist[j])
    try:
        for j in range (14,21):
            for i in range(len(datatlist[14])):
                sheet.write(i+1,j,datatlist[14][i][j-14])
    except:
        pass
    for j in range(21,23):
        sheet.write(1,j,datatlist[j-6])
    try:
        for j in range(23,30):
            for i in range(len(datatlist[17])):
                sheet.write(i+1,j,datatlist[17][i][j-23])
    except:
        pass
    for j in range(30,34):
        sheet.write(1,j,datatlist[j-10])
    book.save(savepath)



def close_driver(driver):
    if len(driver.window_handles) > 1:
        driver.close()


def integration(datalist):
    if len(datalist[14]) != 0:
        for i in range(len(datalist[14])):
            name = datalist[14][i][0].split("（")[0]
            flag = 0
            for item in datalist[18]:
                c_name = item[0].split("（")[0]
                if name == c_name:
                    flag = 1
                    datalist[14][i].append(item[2])
                    datalist[14][i].append(item[3])
                    datalist[14][i].append(item[4])
                    break
            if flag == 0:
                datalist[14][i].append("")
                datalist[14][i].append("")
                datalist[14][i].append("")

    if len(datalist[17]) != 0:
        for i in range(len(datalist[17])):
            name = datalist[17][i][0].split("（")[0]
            flag = 0
            for item in datalist[19]:
                c_name = item[0].split("（")
                if name == c_name:
                    flag = 1
                    datalist[17][i].append(item[2])
                    datalist[17][i].append(item[3])
                    datalist[17][i].append(item[4])
                    break
            if flag == 0:
                datalist[17][i].append("")
                datalist[17][i].append("")
                datalist[17][i].append("")


def get_page_special(driver,datalist):
    datalist.append("")#21
    datalist.append("")#22
    datalist.append("")#23
    datalist.append("")#24
    try:
        item = driver.find_element_by_xpath("/html/body/div[2]/div[1]/div/div/div[3]/div[1]/div[1]/div/div[2]/div")
        divs = item.find_elements_by_xpath("div")
        for div in divs:
            item = div.text.split("\n")
            if item[1] == "双一流":
                datalist[20] = item[0]
            if item[1] == "国家特色":
                datalist[21] = item[0]
            if item[1] == "硕士点":
                datalist[22] = item[0]
            if item[1] == "博士点":
                datalist[23] = item[0]
    except:
        pass


def get_page_ad(driver,datalist):
    select = driver.find_element_by_xpath("/html/body/div[2]/div[1]/div/div/div[4]/div[2]/div[3]/div[2]/div[3]/table/tbody")
    trs = select.find_elements_by_xpath("tr")
    tr_list = []
    for tr in trs:
        tds = tr.find_elements_by_xpath("td")
        td_list = []
        for td in tds:
            td = td.text
            td_list.append(td)
        tr_list.append(td_list)
    datalist.append(tr_list)#一段19，二段20


def set_type_ad(driver,type):
    select = driver.find_element_by_xpath("/html/body/div[2]/div[1]/div/div/div[4]/div[2]/div[3]/div[2]/div[1]/div[3]/select")
    options = select.find_elements_by_xpath("option")
    for option in options:
        if option.text == type:
            option.click()
            time.sleep(0.1)


def set_year_ad(driver):
    select = driver.find_element_by_xpath("/html/body/div[2]/div[1]/div/div/div[4]/div[2]/div[3]/div[2]/div[1]/div[2]/select")
    options = select.find_elements_by_xpath("option")
    flag = 0
    try:
        if options[0].text == "2020":
            options[0].click()
            flag = 1
    except:
        pass
    if flag == 0:
        try:
            if options[1].text == "2020":
                options[1].click()
                flag = 1
        except:
            pass
    time.sleep(0.1)


def set_province_ad(driver):
    select = driver.find_element_by_xpath("/html/body/div[2]/div[1]/div/div/div[4]/div[2]/div[3]/div[2]/div[1]/div[1]/select")
    options = select.find_elements_by_xpath("option")
    options[12].click()
    time.sleep(0.1)


def set_type_first(driver,type):
    select = driver.find_element_by_xpath("/html/body/div[2]/div[1]/div/div/div[2]/div[2]/div[1]/div/div[2]/div[1]/div[3]/select")
    options = select.find_elements_by_xpath("option")
    for option in options:
        if option.text == type:
            option.click()
            time.sleep(0.1)
            return True
    return False


def get_page_score(driver,datalist):
    #录取最低分数线
    try:
        lowest_score = driver.find_element_by_xpath("/html/body/div[2]/div[1]/div/div/div[2]/div[2]/div[1]/div/div[2]/div[3]/table/tbody/tr/td[2]").text
    except:
        lowest_score = ""
    try:
        lowest_position = driver.find_element_by_xpath("/html/body/div[2]/div[1]/div/div/div[2]/div[2]/div[1]/div/div[2]/div[3]/table/tbody/tr/td[3]").text
    except:
        lowest_position = ""
    datalist.append(lowest_score)#13
    datalist.append(lowest_position)#14  一段13 14 二段16 17
    #专业最低分数线
    tbody = driver.find_element_by_xpath("/html/body/div[2]/div[1]/div/div/div[2]/div[2]/div[3]/div[2]/div[3]/table/tbody")
    trs = tbody.find_elements_by_xpath("tr")
    tr_list = []
    for tr in trs:
        tds = tr.find_elements_by_xpath("td")
        td_list = []
        for td in tds:
            td = td.text
            td_list.append(td)
        tr_list.append(td_list)
    datalist.append(tr_list)#15一段专业分数线   18二段专业分数线


def set_year_first(driver):
    select = driver.find_element_by_xpath("/html/body/div[2]/div[1]/div/div/div[2]/div[2]/div[1]/div/div[2]/div[1]/div[2]/select")
    options = select.find_elements_by_xpath("option")
    flag = 0
    try:
        if options[0].text == "2020":
            options[0].click()
            flag = 1
    except:
        pass
    if flag == 0:
        try:
            if options[1] == "2020":
                options[1].click()
                flag = 1
        except:
            pass
    if flag == 0:
        return -1
    time.sleep(0.1)


def set_year_second(driver):
    select = driver.find_element_by_xpath("/html/body/div[2]/div[1]/div/div/div[2]/div[2]/div[3]/div[2]/div[1]/div[2]/select")
    options = select.find_elements_by_xpath("option")
    flag = 0
    try:
        if options[0] == "2020":
            options[0].click()
            flag = 1
    except:
        pass
    if flag == 0:
        try:
            if options[1] == "2020":
                options[1].click()
                flag = 1
        except:
            pass
    if flag == 0:
        return -1
    time.sleep(0.1)


def set_province_first(driver):
    select = driver.find_element_by_xpath("/html/body/div[2]/div[1]/div/div/div[2]/div[2]/div[1]/div/div[2]/div[1]/div[1]/select")
    options = select.find_elements_by_xpath("option")
    options[12].click()
    time.sleep(0.1)


def set_province_second(driver):
    select = driver.find_element_by_xpath("/html/body/div[2]/div[1]/div/div/div[2]/div[2]/div[3]/div[2]/div[1]/div[1]/select")
    options = select.find_elements_by_xpath("option")
    options[12].click()
    time.sleep(0.1)


def get_page_pre(driver,baseurl,datalist):
    driver.get(baseurl)
    school_name = driver.find_element_by_xpath("/html/body/div/article/section/div[1]/div[1]/div/div[2]/div/div[2]/div[1]/div/div[1]/div[1]/div/a/div/span").text
    try:
        school_code = get_school_code(school_name,driver)
    except:
        school_code = "-1"
    try:
        school_type,province = get_school_type_province(driver,school_name)
    except:
        school_type = "-1"
        province = "-1"
    try:
        pic_href = get_school_png_href(driver,school_name)
    except:
        pic_href = ""
    driver.get(baseurl)
    try:
        label1 = driver.find_element_by_xpath("/html/body/div/article/section/div[1]/div[1]/div/div[2]/div/div[2]/div[1]/div/div[2]/a/span[1]/span").text
    except:
        label1 = ""
    try:
        label2 = driver.find_element_by_xpath("/html/body/div/article/section/div[1]/div[1]/div/div[2]/div/div[2]/div[1]/div/div[2]/a/span[2]").text
    except:
        label2 = ""
    try:
        introduce = driver.find_element_by_xpath("/html/body/div/article/section/div[1]/div[1]/div/div[2]/div/div[2]/div[3]/a[1]/div/div/span").text
    except:
        introduce = ""
    try:
        address = driver.find_element_by_xpath("/html/body/div/article/section/div[1]/div[1]/div/div[2]/div/div[2]/div[3]/a[2]/div/div/span").text
    except:
        address = ""
    try:
        work_rate = driver.find_element_by_xpath("/html/body/div/article/section/div[1]/div[1]/div/div[6]/div/div/div[2]/div/div/div/div[1]/div/div/div[1]").text
    except:
        work_rate = ""
    try:
        abord_rate = driver.find_element_by_xpath("/html/body/div/article/section/div[1]/div[1]/div/div[6]/div/div/div[2]/div/div/div/div[2]/div/div/div[1]").text
    except:
        abord_rate = ""
    try:
        yan_rate = driver.find_element_by_xpath("/html/body/div/article/section/div[1]/div[1]/div/div[6]/div/div/div[2]/div/div/div/div[3]/div/div/div[1]").text
    except:
        yan_rate = ""
    datalist.append(school_name)#1
    datalist.append(school_code)#2
    datalist.append(school_type)#3
    datalist.append(province)#4
    datalist.append(pic_href)#5
    datalist.append(label1)#6
    datalist.append(label2)#7
    datalist.append(introduce)#8
    datalist.append(address)#9
    datalist.append(work_rate)#10
    datalist.append(abord_rate)#11
    datalist.append(yan_rate)#12


def get_school_png_href(driver,school_name):
    driver.get("https://gkcx.eol.cn/")
    ele = driver.find_element_by_xpath("/html/body/div[1]/div/div[1]/div/div/div/div[1]/div[2]/div[3]/div/div[1]/div/input")
    ele.send_keys(school_name)
    driver.find_element_by_xpath("/html/body/div[1]/div/div[1]/div/div/div/div[1]/div[2]/div[3]/div/div[1]/div/button").click()
    time.sleep(0.5)
    href = driver.find_element_by_xpath("/html/body/div[1]/div/div[1]/div/div/div/div[1]/div[3]/div[2]/div[2]/div/ul/li[1]/img").get_attribute("src")
    close_driver(driver)
    return href


def get_school_type_province(driver,school_name):

    base1 = "http://daxue.566.com/collegelist/?keyword="
    base2 = str(parse.quote(school_name))
    base3 = "&ProvinceID=0&leixingID=0&xingzhi=&button1=搜索"
    type_url = base1 + base2 +base3
    driver.get(type_url)
    name = driver.find_element_by_xpath("/html/body/div[5]/div[2]/div[2]/table/tbody/tr[2]/td[1]/a").text
    type = driver.find_element_by_xpath("/html/body/div[5]/div[2]/div[2]/table/tbody/tr[2]/td[3]").text
    province = driver.find_element_by_xpath("/html/body/div[5]/div[2]/div[2]/table/tbody/tr[2]/td[2]").text
    close_driver(driver)
    if school_name == name:
        return type,province
    else:
        return "-1,-1"


def get_school_code(school_name,driver):
    ff = open("d://code.txt","r")
    line = ff.readline()
    while line:
        name = line[6:len(str(line))-1]
        if school_name == name:
            code = line[:6]
            ff.close()
            return code
        else:
            line = ff.readline()
    ff.close()
    try:
        driver.get("http://www.codexy.cn/xycode/#/")
        driver.find_element_by_xpath("/html/body/div[1]/section/div[1]/header/div/div[2]/div/input").send_keys(school_name)
        code = driver.find_element_by_xpath("/html/body/div[1]/section/div[2]/main/div/div[3]/table/tbody/tr/td[1]/div/div").text
        close_driver(driver)
        return code
    except:
        try:
            driver.get("http://gjcxcy.bjtu.edu.cn/Help/FindSchoolCode.aspx")
            driver.find_element_by_xpath("/html/body/form/div[3]/div/div[1]/div[2]/div[1]/div[4]/input").send_keys(school_name)
            driver.find_element_by_xpath("/html/body/form/div[3]/div/div[1]/div[2]/div[2]/input").click()
            time.sleep(0.1)
            code = driver.find_element_by_xpath("/html/body/form/div[3]/div/div[2]/div[1]/table/tbody/tr[1]/td[4]").text
            return code
        except:
            return -1



def get_baseurl(line):
    base1 = "https://quark.sm.cn/s?q="
    base3 = "&uc_param_str=dnntnwvepffrgibijbprsvdsdichmenn#general_entity_college_domestic_max_lg/index/"
    base2 = line[:len(str(line))-7]
    baseurl = base1 + base2 + base3
    return baseurl


if __name__=="__main__":
    main()