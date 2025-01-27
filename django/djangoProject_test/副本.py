# -*- coding:utf-8 -*-

import xlwt  # 进行excel操作
from selenium import webdriver
from selenium import *
from selenium import webdriver
from urllib import parse
import time
import pymysql
import smtplib
from email.mime.multipart import MIMEMultipart
from email.header import Header
from email.mime.text import MIMEText
from email.mime.application import MIMEApplication
import xlrd
from xlutils.copy import copy
import os
from selenium.webdriver.chrome.options import Options

# Create your views here.


def message_config():
    """
    3.配置邮件信息
    :return: 消息对象
    """
    # 第三方 SMTP 服务
    content = MIMEText('爬取完毕，请注意查收数据')
    message = MIMEMultipart()  # 多个MIME对象
    message.attach(content)  # 添加内容
    message['From'] = Header("高考志愿填报团队", 'utf-8')  # 发件人
    message['To'] = Header("甲方", 'utf-8')  # 收件人
    message['Subject'] = Header('高校数据', 'utf-8')  # 主题
    # 添加Excel类型附件
    file_name = 'all_schools//data.xls'  # 文件名
    file_path = os.path.join(file_name)  # 文件路径
    xlsx = MIMEApplication(open(file_path, 'rb').read())  # 打开Excel,读取Excel文件
    xlsx["Content-Type"] = 'application/octet-stream'  # 设置内容类型
    xlsx.add_header('Content-Disposition', 'attachment', filename=file_name)  # 添加到header信息
    message.attach(xlsx)
    return message


def send_mail(message):
    mail_host = "smtp.qq.com"  # 设置服务器
    mail_user = "3272802661@qq.com"  # 用户名
    mail_pass = "vpkibriezknwdajf"  # 口令
    # 配置发件人、收件人信息
    sender = '3272802661@qq.com'  # 发件人邮箱
    receivers = ['3272802661@qq.com']  # 接收邮件，可设置为多个邮箱
    try:
        smtpObj = smtplib.SMTP_SSL(mail_host)  # 使用SSL连接邮箱服务器
        smtpObj.login(mail_user, mail_pass)  # 登录服务器
        smtpObj.sendmail(sender, receivers, message.as_string())  # 发送邮件
        print("邮件发送成功")
    except Exception as e:
        print(e)


def connect_mysql():
    return pymysql.connect(host="127.0.0.1", user="root", passwd="111111", charset="utf8", database="Gaokao")


def savedata(school_name, datatlist):
    savepath = "all_schools//" + str(school_name) + ".xls"
    book = xlwt.Workbook(encoding="utf-8", style_compression=0)
    sheet = book.add_sheet('院校信息', cell_overwrite_ok=True)
    col = ('名称', '学校代码', '学校类型', '省份', '校徽链接', '标签1', '标签2', '简介', '地址', '就业率', '出国率', '考研率', '一段最低分', '一段最低位次', '专业名称',
           '选考要求', '最低分', '最低位次', '录取人数', '学制', '学费', '二段最低分', '二段最低位次', '专业名称', '选考要求', '最低分', '最低位次', '录取人数', '学制',
           '学费', '双一流', '国家特色', '硕士点', '博士点')  # 33
    for j in range(len(col)):
        sheet.write(0, j, col[j])
    for j in range(14):
        sheet.write(1, j, datatlist[j])
    try:
        for j in range(14, 21):
            for i in range(len(datatlist[14])):
                sheet.write(i + 1, j, datatlist[14][i][j - 14])
    except:
        pass
    for j in range(21, 23):
        sheet.write(1, j, datatlist[j - 6])
    try:
        for j in range(23, 30):
            for i in range(len(datatlist[17])):
                sheet.write(i + 1, j, datatlist[17][i][j - 23])
    except:
        pass
    for j in range(30, 34):
        sheet.write(1, j, datatlist[j - 10])
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


def get_page_special(driver, datalist):
    datalist.append("")  # 21
    datalist.append("")  # 22
    datalist.append("")  # 23
    datalist.append("")  # 24
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


def get_page_ad(driver, datalist):
    select = driver.find_element_by_xpath(
        "/html/body/div[2]/div[1]/div/div/div[4]/div[2]/div[3]/div[2]/div[3]/table/tbody")
    trs = select.find_elements_by_xpath("tr")
    tr_list = []
    for tr in trs:
        tds = tr.find_elements_by_xpath("td")
        td_list = []
        for td in tds:
            td = td.text
            td_list.append(td)
        tr_list.append(td_list)
    datalist.append(tr_list)  # 一段19，二段20


def set_type_ad(driver, type):
    select = driver.find_element_by_xpath(
        "/html/body/div[2]/div[1]/div/div/div[4]/div[2]/div[3]/div[2]/div[1]/div[3]/select")
    options = select.find_elements_by_xpath("option")
    for option in options:
        if option.text == type:
            option.click()
            time.sleep(0.1)


def set_year_ad(driver):
    select = driver.find_element_by_xpath(
        "/html/body/div[2]/div[1]/div/div/div[4]/div[2]/div[3]/div[2]/div[1]/div[2]/select")
    options = select.find_elements_by_xpath("option")
    flag = 0
    try:
        if options[0].text == "2021":
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
    select = driver.find_element_by_xpath(
        "/html/body/div[2]/div[1]/div/div/div[4]/div[2]/div[3]/div[2]/div[1]/div[1]/select")
    options = select.find_elements_by_xpath("option")
    options[12].click()
    time.sleep(0.1)


def set_type_first(driver, type):
    select = driver.find_element_by_xpath(
        "/html/body/div[2]/div[1]/div/div/div[2]/div[2]/div[1]/div/div[2]/div[1]/div[3]/select")
    options = select.find_elements_by_xpath("option")
    for option in options:
        if option.text == type:
            option.click()
            time.sleep(0.1)
            return True
    return False


def get_page_score(driver, datalist):
    # 录取最低分数线
    try:
        lowest_score = driver.find_element_by_xpath(
            "/html/body/div[2]/div[1]/div/div/div[2]/div[2]/div[1]/div/div[2]/div[3]/table/tbody/tr/td[2]").text
    except:
        lowest_score = ""
    try:
        lowest_position = driver.find_element_by_xpath(
            "/html/body/div[2]/div[1]/div/div/div[2]/div[2]/div[1]/div/div[2]/div[3]/table/tbody/tr/td[3]").text
    except:
        lowest_position = ""
    datalist.append(lowest_score)  # 13
    datalist.append(lowest_position)  # 14  一段13 14 二段16 17
    # 专业最低分数线
    tbody = driver.find_element_by_xpath(
        "/html/body/div[2]/div[1]/div/div/div[2]/div[2]/div[3]/div[2]/div[3]/table/tbody")
    trs = tbody.find_elements_by_xpath("tr")
    tr_list = []
    for tr in trs:
        tds = tr.find_elements_by_xpath("td")
        td_list = []
        for td in tds:
            td = td.text
            td_list.append(td)
        tr_list.append(td_list)
    datalist.append(tr_list)  # 15一段专业分数线   18二段专业分数线


def set_year_first(driver):
    select = driver.find_element_by_xpath(
        "/html/body/div[2]/div[1]/div/div/div[2]/div[2]/div[1]/div/div[2]/div[1]/div[2]/select")
    options = select.find_elements_by_xpath("option")
    flag = 0
    try:
        if options[0].text == "2021":
            options[0].click()
            flag = 1
    except:
        pass
    if flag == 0:
        try:
            if options[1].text == "2021":
                options[1].click()
                flag = 1
        except:
            pass
    if flag == 0:
        return -1
    time.sleep(0.1)


def set_year_second(driver):
    select = driver.find_element_by_xpath(
        "/html/body/div[2]/div[1]/div/div/div[2]/div[2]/div[3]/div[2]/div[1]/div[2]/select")
    options = select.find_elements_by_xpath("option")
    flag = 0
    try:
        if options[0] == "2021":
            options[0].click()
            flag = 1
    except:
        pass
    if flag == 0:
        try:
            if options[1].text == "2021":
                options[1].click()
                flag = 1
        except:
            pass
    if flag == 0:
        return -1
    time.sleep(0.1)


def set_province_first(driver):
    select = driver.find_element_by_xpath(
        "/html/body/div[2]/div[1]/div/div/div[2]/div[2]/div[1]/div/div[2]/div[1]/div[1]/select")
    options = select.find_elements_by_xpath("option")
    options[12].click()
    time.sleep(0.1)


def set_province_second(driver):
    select = driver.find_element_by_xpath(
        "/html/body/div[2]/div[1]/div/div/div[2]/div[2]/div[3]/div[2]/div[1]/div[1]/select")
    options = select.find_elements_by_xpath("option")
    options[12].click()
    time.sleep(0.1)


def get_page_pre(driver, baseurl, datalist):
    driver.get(baseurl)
    time.sleep(0.5)
    school_name = driver.find_element_by_xpath(
        "/html/body/div/article/section/div[1]/div[1]/div/div[2]/div/div[2]/div[1]/div/div[1]/div[1]/div/a/div/span").text
    try:
        school_code = get_school_code(school_name, driver)
    except:
        school_code = "-1"
    try:
        school_type, province = get_school_type_province(driver, school_name)
    except:
        school_type = "-1"
        province = "-1"
    try:
        pic_href = get_school_png_href(driver, school_name)
    except:
        pic_href = ""
    driver.get(baseurl)
    try:
        label1 = driver.find_element_by_xpath(
            "/html/body/div/article/section/div[1]/div[1]/div/div[2]/div/div[2]/div[1]/div/div[2]/a/span[1]/span").text
    except:
        label1 = ""
    try:
        label2 = driver.find_element_by_xpath(
            "/html/body/div/article/section/div[1]/div[1]/div/div[2]/div/div[2]/div[1]/div/div[2]/a/span[2]").text
    except:
        label2 = ""
    try:
        introduce = driver.find_element_by_xpath(
            "/html/body/div/article/section/div[1]/div[1]/div/div[2]/div/div[2]/div[3]/a[1]/div/div/span").text
    except:
        introduce = ""
    try:
        address = driver.find_element_by_xpath(
            "/html/body/div/article/section/div[1]/div[1]/div/div[2]/div/div[2]/div[3]/a[2]/div/div/span").text
    except:
        address = ""
    try:
        work_rate = driver.find_element_by_xpath(
            "/html/body/div/article/section/div[1]/div[1]/div/div[6]/div/div/div[2]/div/div/div/div[1]/div/div/div[1]").text
    except:
        work_rate = ""
    try:
        abord_rate = driver.find_element_by_xpath(
            "/html/body/div/article/section/div[1]/div[1]/div/div[6]/div/div/div[2]/div/div/div/div[2]/div/div/div[1]").text
    except:
        abord_rate = ""
    try:
        yan_rate = driver.find_element_by_xpath(
            "/html/body/div/article/section/div[1]/div[1]/div/div[6]/div/div/div[2]/div/div/div/div[3]/div/div/div[1]").text
    except:
        yan_rate = ""
    datalist.append(school_name)  # 1
    datalist.append(school_code)  # 2
    datalist.append(school_type)  # 3
    datalist.append(province)  # 4
    datalist.append(pic_href)  # 5
    datalist.append(label1)  # 6
    datalist.append(label2)  # 7
    datalist.append(introduce)  # 8
    datalist.append(address)  # 9
    datalist.append(work_rate)  # 10
    datalist.append(abord_rate)  # 11
    datalist.append(yan_rate)  # 12


def get_school_png_href(driver, school_name):
    driver.get("https://gkcx.eol.cn/")
    ele = driver.find_element_by_xpath(
        "/html/body/div[1]/div/div[1]/div/div/div/div[1]/div[2]/div[3]/div/div[1]/div/input")
    ele.send_keys(school_name)
    driver.find_element_by_xpath(
        "/html/body/div[1]/div/div[1]/div/div/div/div[1]/div[2]/div[3]/div/div[1]/div/button").click()
    time.sleep(0.5)
    href = driver.find_element_by_xpath(
        "/html/body/div[1]/div/div[1]/div/div/div/div[1]/div[3]/div[2]/div[2]/div/ul/li[1]/img").get_attribute("src")
    close_driver(driver)
    return href


def get_school_type_province(driver, school_name):
    base1 = "http://daxue.566.com/collegelist/?keyword="
    base2 = str(parse.quote(school_name))
    base3 = "&ProvinceID=0&leixingID=0&xingzhi=&button1=搜索"
    type_url = base1 + base2 + base3
    driver.get(type_url)
    name = driver.find_element_by_xpath("/html/body/div[5]/div[2]/div[2]/table/tbody/tr[2]/td[1]/a").text
    type = driver.find_element_by_xpath("/html/body/div[5]/div[2]/div[2]/table/tbody/tr[2]/td[3]").text
    province = driver.find_element_by_xpath("/html/body/div[5]/div[2]/div[2]/table/tbody/tr[2]/td[2]").text
    close_driver(driver)
    if school_name == name:
        return type, province
    else:
        return "-1,-1"


def get_school_code(school_name, driver):
    ff = open("profile//code.txt", "r")
    line = ff.readline()
    while line:
        name = line[6:len(str(line)) - 1]
        if school_name == name:
            code = line[:6]
            ff.close()
            return code
        else:
            line = ff.readline()
    ff.close()
    try:
        driver.get("http://www.codexy.cn/xycode/#/")
        driver.find_element_by_xpath("/html/body/div[1]/section/div[1]/header/div/div[2]/div/input").send_keys(
            school_name)
        code = driver.find_element_by_xpath(
            "/html/body/div[1]/section/div[2]/main/div/div[3]/table/tbody/tr/td[1]/div/div").text
        close_driver(driver)
        return code
    except:
        try:
            driver.get("http://gjcxcy.bjtu.edu.cn/Help/FindSchoolCode.aspx")
            driver.find_element_by_xpath("/html/body/form/div[3]/div/div[1]/div[2]/div[1]/div[4]/input").send_keys(
                school_name)
            driver.find_element_by_xpath("/html/body/form/div[3]/div/div[1]/div[2]/div[2]/input").click()
            time.sleep(0.1)
            code = driver.find_element_by_xpath("/html/body/form/div[3]/div/div[2]/div[1]/table/tbody/tr[1]/td[4]").text
            return code
        except:
            return -1


def get_baseurl(line):
    base1 = "https://quark.sm.cn/s?q="
    base3 = "&uc_param_str=dnntnwvepffrgibijbprsvdsdichmenn#general_entity_college_domestic_max_lg/index/"
    base2 = line[:len(str(line)) - 7]
    baseurl = base1 + base2 + base3
    return baseurl


def create_table(cursor):
    # 创建表
    # cursor.execute('drop table if exists spider_state')
    cursor.execute("create table spider_state (id bigint auto_increment not null comment '主键',start_time bigint not null default -1 comment '开始时间',end_time bigint not null default -1 comment '结束时间',state tinyint not null default -1 comment '状态码 0 表示正在运行 1 表示已经结束',primary key(id)) engine = InnoDB default charset = utf8mb4 comment '爬虫状态表'")
    cursor.execute("alter table spider_state convert to character set utf8")


def spider_start(start_time, cursor, database):
    value = ()
    value += (start_time, -1, 0)
    sql = "insert into spider_state (start_time, end_time, state) values (%s,%s,%s)"
    cursor.execute(sql, value)
    database.commit()


def spider_end( end_time, cursor, database):
    sql = "select id from spider_state order by id DESC limit 1"
    cursor.execute(sql)
    database.commit()
    ID = cursor.fetchall()[0][0]
    sql = "update spider_state set end_time = '{}' where id = '{}'".format(end_time,ID)
    cursor.execute(sql)
    database.commit()
    sql = "update spider_state set state = '{}' where id = '{}'".format(1,ID)
    cursor.execute(sql)
    database.commit()



def Send_mail():
    print("开始执行")
    message = message_config()  # 调用配置方法
    send_mail(message)  # 发送邮件
    print("执行结束")


def add_shi(name_of_shi):
    f_shi = open("profile//市.txt", encoding="utf8")
    line = f_shi.readline()
    while line:
        line = line.replace("\n", "")
        line = line.split(" ")
        for item in line:
            item = item.replace("市", "")
            name_of_shi.append(item)
        line = f_shi.readline()
    name_of_shi.remove("")
    f_shi.close()


def province_and_shi(province_and_shi_names):
    f = open("profile//province_and_shi.txt", encoding="utf8")
    line = f.readline().replace("\n", "")
    while line:
        line = line.split(" ")
        province = line[0].replace("省", "")
        line = line[1:]
        for item in line:
            item = item.replace("市", "")
            item = item.replace("\n", "")
            province_and_shi_names.append(province + "," + item)
        line = f.readline().replace("\n", "")
    f.close()


def insert_data(data_book, sheet, data_sheet, name_of_shi, province_and_shi_names, index):
    ################################################
    address_detail = sheet.cell(1, 8).value
    shi_flag = 0
    shiname = ""
    for item in name_of_shi:
        if item in address_detail:
            shiname = item  # 市5
            shi_flag = 1
            break
    if shi_flag == 0:
        shiname = "-1"
    ################################################
    len_one = 0
    len_two = 0
    while True:
        try:
            if sheet.cell(len_one + 1, 14).value != "":
                len_one += 1
            else:
                break
        except:
            break
    while True:
        try:
            if sheet.cell(len_two + 1, 22).value != "":
                len_two += 1
            else:
                break
        except:
            break

    for i in range(len_one):  # 遍历一段专业
        value = ()
        value += (index,)  # 1
        value += (sheet.cell(1, 0).value,)  # 学校名字2
        value += (sheet.cell(1, 1).value,)  # 学校代码3
        # value += (sheet.cell(1,3).value,)#所在省份4
        ##################################################
        # 省份
        if sheet.cell(1, 3).value == "-2" or sheet.cell(1, 3).value == "-1":
            info = sheet.cell(1, 8).value  # 地址信息中寻找省份信息
            province_list = ['上海', '海南', '北京', '天津', '湖南', '湖北', '江苏', '福建', '广东', '河北', '辽宁', '重庆', '山东', '浙江', '河南',
                             '四川', '安徽', '广西', '贵州', '江西', '云南', '山西', '陕西', '甘肃', '新疆', '黑龙江', '内蒙古', '吉林', '宁夏', '青海',
                             '西藏']
            flag = False
            for item in province_list:
                if item in info:
                    value += (item,)
                    flag = True
                    break

            if flag == False:
                flag2 = False
                for item in province_and_shi_names:
                    if shiname in item:
                        value += (item.split(",")[0],)
                        flag2 = True
                        break
                if flag2 == False:
                    value += ("-1",)  # 没有省份信息
        else:
            value += (sheet.cell(1, 3).value,)
        value += (str(shiname),)  # 市5
        value += (sheet.cell(1, 8).value,)  # 详细地址6
        is_undergraduate_school = 0
        if "本科" in sheet.cell(1, 5).value:
            is_undergraduate_school = 1
        else:
            is_undergraduate_school = 0
        value += (is_undergraduate_school,)  # 是不是本科7
        is_junior_school = 0
        if "专科" in sheet.cell(1, 5).value:
            is_junior_school = 1
        else:
            is_junior_school = 0
        value += (is_junior_school,)  # 是不是专科8
        is_985 = 0
        if "985" in sheet.cell(1, 6).value:
            is_985 = 1
        else:
            is_985 = 0
        value += (is_985,)  # 是不是985 9
        is_211 = 0
        if "985" in sheet.cell(1, 6).value or "211" in sheet.cell(i, 6).value:
            is_211 = 1
        else:
            is_211 = 0
        value += (is_211,)  # 是不是211 10
        is_public = 0
        if "公办" in sheet.cell(1, 5).value:
            is_public = 1
        else:
            is_public = 0
        value += (is_public,)  # 是不是公办 11
        is_private = 0
        if "民办" in sheet.cell(1, 5).value:
            is_private = 1
        else:
            is_private = 0
        value += (is_private,)  # 是不是民办12
        value += (sheet.cell(1, 2).value,)  # 院校类型13
        value += (sheet.cell(1, 4).value,)  # 校徽链接14
        if sheet.cell(1, 9).value != "":
            try:
                value += (int(sheet.cell(1, 9).value.replace("%", "")),)  # 就业率15
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(1, 10).value != "":
            try:
                value += (int(sheet.cell(1, 10).value.replace("%", "")),)  # 出国率16
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(1, 11).value != "":
            try:
                value += (int(sheet.cell(1, 11).value.replace("%", "")),)  # 考研率17
            except:
                value += (-1,)
        else:
            value += (-1,)
        value += (1,)  # 专业类型18
        if sheet.cell(1, 12).value != "":
            value += (int(sheet.cell(1, 12).value),)  # 一段最低分19
        else:
            value += (-1,)
        if sheet.cell(1, 13).value != "":
            value += (int(sheet.cell(1, 13).value),)  # 一段最低位次20
        else:
            value += (-1,)
        value += (sheet.cell(i + 1, 14).value,)  # 专业名称21
        ###################################################################
        res = judge_type(sheet.cell(i + 1, 15).value)  # 选科和限制
        value += (int(res[0]),)  # 选科要求类型22
        temp = res[1].split(",")
        ans = "["
        flag = 0
        for it in temp:
            if flag == 0:
                flag = 1
                ans += str(it)
            else:
                ans += ","
                ans += str(it)
        ans += "]"
        value += (ans,)  # 具体要求科目23
        if sheet.cell(i + 1, 16).value != "":
            try:
                value += (int(sheet.cell(i + 1, 16).value),)  # 专业最低分24
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(i + 1, 17).value != "":
            try:
                value += (int(sheet.cell(i + 1, 17).value),)  # 专业最低位次25
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(i + 1, 18).value != "":
            try:
                value += (int(sheet.cell(i + 1, 18).value),)  # 录取人数26
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(i + 1, 19).value != "":
            try:
                value += (int(sheet.cell(i + 1, 19).value),)  # 学制27
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(i + 1, 20).value != "" and sheet.cell(i + 1, 20).value != '--':
            try:
                value += (int(sheet.cell(i + 1, 20).value),)  # 学费28
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(1, 30).value != "":
            try:
                value += (int(sheet.cell(1, 30).value),)  # 双一流29
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(1, 31).value != "":
            try:
                value += (int(sheet.cell(1, 31).value),)  # 国家特色30
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(1, 32).value != "":
            try:
                value += (int(sheet.cell(1, 32).value),)  # 硕士点31
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(1, 33).value != "":
            try:
                value += (int(sheet.cell(1, 33).value),)  # 博士点32
            except:
                value += (-1,)
        else:
            value += (-1,)
        # 向excel表中写数据
        for j in range(32):
            data_sheet.write(index, j, value[j])
        index += 1

    for i in range(len_two):  # 遍历一段专业
        value = ()
        value += (index,)  # 1
        value += (sheet.cell(1, 0).value,)  # 学校名字2
        value += (sheet.cell(1, 1).value,)  # 学校代码3
        if sheet.cell(1, 3).value == "-2" or sheet.cell(1, 3).value == "-1":
            info = sheet.cell(1, 8).value  # 地址信息中寻找省份信息
            province_list = ['上海', '海南', '北京', '天津', '湖南', '湖北', '江苏', '福建', '广东', '河北', '辽宁', '重庆', '山东', '浙江', '河南',
                             '四川', '安徽', '广西', '贵州', '江西', '云南', '山西', '陕西', '甘肃', '新疆', '黑龙江', '内蒙古', '吉林', '宁夏', '青海',
                             '西藏']
            flag = False
            for item in province_list:
                if item in info:
                    value += (item,)
                    flag = True
                    break

            if flag == False:
                flag2 = False
                for item in province_and_shi_names:
                    if shiname in item:
                        value += (item.split(",")[0],)
                        flag2 = True
                        break
                if flag2 == False:
                    value += ("-1",)  # 没有省份信息
        else:
            value += (sheet.cell(1, 3).value,)
        value += (str(shiname),)  # 市5
        value += (sheet.cell(1, 8).value,)  # 详细地址6
        is_undergraduate_school = 0
        if "本科" in sheet.cell(1, 5).value:
            is_undergraduate_school = 1
        else:
            is_undergraduate_school = 0
        value += (is_undergraduate_school,)  # 是不是本科7
        is_junior_school = 0
        if "专科" in sheet.cell(1, 5).value:
            is_junior_school = 1
        else:
            is_junior_school = 0
        value += (is_junior_school,)  # 是不是专科8
        is_985 = 0
        if "985" in sheet.cell(1, 5).value:
            is_985 = 1
        else:
            is_985 = 0
        value += (is_985,)  # 是不是985 9
        is_211 = 0
        if "985" in sheet.cell(1, 5).value or "211" in sheet.cell(i, 6).value:
            is_211 = 1
        else:
            is_211 = 0
        value += (is_211,)  # 是不是211 10
        is_public = 0
        if "公办" in sheet.cell(1, 5).value:
            is_public = 1
        else:
            is_public = 0
        value += (is_public,)  # 是不是公办 11
        is_private = 0
        if "民办" in sheet.cell(1, 5).value:
            is_private = 1
        else:
            is_private = 0
        value += (is_private,)  # 是不是民办12
        value += (sheet.cell(1, 2).value,)  # 院校类型13
        value += (sheet.cell(1, 4).value,)  # 校徽链接14
        if sheet.cell(1, 9).value != "":
            try:
                value += (int(sheet.cell(1, 9).value.replace("%", "")),)  # 就业率15
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(1, 10).value != "":
            try:
                value += (int(sheet.cell(1, 10).value.replace("%", "")),)  # 出国率16
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(1, 11).value != "":
            try:
                value += (int(sheet.cell(1, 11).value.replace("%", "")),)  # 考研率17
            except:
                value += (-1,)
        else:
            value += (-1,)
        value += (2,)  # 专业类型18
        if sheet.cell(1, 21).value != "":
            value += (int(sheet.cell(1, 21).value),)  # 二段最低分19
        else:
            value += (-1,)
        if sheet.cell(1, 22).value != "":
            value += (int(sheet.cell(1, 22).value),)  # 二段最低位次20
        else:
            value += (-1,)
        value += (sheet.cell(i + 1, 23).value,)  # 专业名称21
        res = judge_type(sheet.cell(i + 1, 24).value)  # 选科和限制
        value += (int(res[0]),)  # 选科要求类型22
        temp = res[1].split(",")
        ans = "["
        flag = 0
        for it in temp:
            if flag == 0:
                flag = 1
                ans += str(it)
            else:
                ans += ","
                ans += str(it)
        ans += "]"
        value += (ans,)  # 具体要求科目23
        if sheet.cell(i + 1, 25).value != "":
            try:
                value += (int(sheet.cell(i + 1, 25).value),)  # 专业最低分24
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(i + 1, 26).value != "":
            try:
                value += (int(sheet.cell(i + 1, 26).value),)  # 专业最低位次25
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(i + 1, 27).value != "":
            try:
                value += (int(sheet.cell(i + 1, 27).value),)  # 录取人数26
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(i + 1, 28).value != "":
            try:
                value += (int(sheet.cell(i + 1, 28).value),)  # 学制27
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(i + 1, 29).value != "" and sheet.cell(i + 1, 20).value != '--':
            try:
                value += (int(sheet.cell(i + 1, 29).value),)  # 学费28
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(1, 30).value != "":
            try:
                value += (int(sheet.cell(1, 30).value),)  # 双一流29
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(1, 31).value != "":
            try:
                value += (int(sheet.cell(1, 31).value),)  # 国家特色30
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(1, 32).value != "":
            try:
                value += (int(sheet.cell(1, 32).value),)  # 硕士点31
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(1, 33).value != "":
            try:
                value += (int(sheet.cell(1, 33).value),)  # 博士点32
            except:
                value += (-1,)
        else:
            value += (-1,)
        # 向excel表中写数据
        for j in range(32):
            data_sheet.write(index, j, value[j])
        index += 1

    return index





def create_table_excel(cursor):
    cursor.execute("drop table if exists tb_volunteer")
    cursor.execute(
        "CREATE TABLE tb_volunteer (id bigint auto_increment not null comment '主键', name varchar(11) not null default '' comment '高校名称', university_code varchar(11) not null default '' comment '高校代码', province varchar(20) not null default '' comment '所在省份', city varchar(20) not null default '' comment '所在市', address varchar(101) not null default '' comment '详细地址', is_undergraduate_school tinyint(3) not null default 0 comment '1表示是本科否则0', is_junior_school tinyint(3) not null default 0 comment '1表示专科否则0', is_985 tinyint(3) not null default 0 comment '1表示是985否则0', is_211 tinyint(3) not null default 0  comment '1表示211否则0', is_public tinyint(3) not null default 0 comment '1表示公办否则0', is_private tinyint(3) not null default 0 comment '1表示民办否则0', category varchar(11) not null default '' comment '学校分类，理工/综合/师范', pic_link varchar(601) not null default '' comment '校徽链接', employment_rate tinyint(3) not null default 0 comment '就业率', abroad_rate tinyint(3) not null default 0 comment '出国率', further_rate tinyint(3) not null default 0 comment '考研率', volunteer_section tinyint(3) not null default 0 comment '录取批次', lowest_score int not null default 0 comment '录取最低分', lowest_position int not null default 0 comment '录取最低位次', professional_name varchar(255) not null default '' comment '专业名称', subject_restriction_type tinyint(3) not null default 0 comment '选科要求类型', subject_restriction_detail varchar(20) not null default '' comment '具体选科要求', score int not null default 0 comment '专业最低分', position bigint(20) not null default 0 comment '专业最低位次', enrollment int not null default 0 comment '录取人数', time tinyint(3) not null default 0 comment '学制', fee int not null default 0 comment '学费', double_first_class_subject_number int not null default 0 comment '双一流学科数', country_specific_subject_number int not null default 0 comment '国家特色专业数量', master_point int not null default 0 comment '硕士点', doctor_point int not null default 0 comment '博士点',primary key (id)) engine = InnoDB default charset = utf8mb4 comment '用户志愿表'")
    cursor.execute("alter table tb_volunteer convert to character set utf8")


def judge_type(string):
    if "(" in string:
        if "2科必选" in string:
            return "2", choice_code_2(string)
        elif "3科必选" in string:
            return "3", choice_code_356(string)
        elif "2选1" in string:
            return "4", choice_code_2(string)
        elif "3选1" in string:
            return "5", choice_code_356(string)
        elif "3选2" in string:
            return "6", choice_code_356(string)
        else:
            return "-1", "-1"
    else:
        if "不限" in string:
            return "0", "0"
        elif "必选" in string:
            return "1", choice_code_1(string)  # 单科必选
        else:
            return "-1", "-1"


def choice_code_356(string):  # 3选1 三科必选  三选二
    flag = 0
    code = ""
    if "物" in string:
        code += "1,"
        flag += 1
    if "化" in string:
        code += "2,"
        flag += 1
    if "生" in string:
        if flag == 2:
            code += "3"
            return code
        else:
            code += "3,"
            flag += 1
    if "史" in string:
        if flag == 2:
            code += "4"
            return code
        else:
            code += "4,"
            flag += 1
    if "地" in string:
        if flag == 2:
            code += "5"
            return code
        else:
            code += "5,"
            flag += 1
    if "政" in string:
        if flag == 2:
            code += "6"
            return code
        else:
            return "-1"


def choice_code_2(string):  # 2选1
    code = ""
    if "物理" in string:
        code = "1,"
    if "化学" in string:
        if code != "":
            code = code + "2"
            return code
        else:
            code = "2,"
    if "生物" in string:
        if code != "":
            code = code + "3"
            return code
        else:
            code = "3,"
    if "历史" in string:
        if code != "":
            code = code + "4"
            return code
        else:
            code = "4,"
    if "地理" in string:
        if code != "":
            code = code + "5"
            return code
        else:
            code = "5,"
    if "思想政治" in string:
        code = code + "6"
        return code
    else:
        code = "-1"
        return code


def choice_code_2(string):  # 两科必选
    code = ""
    if "物理" in string:
        code += "1,"
    if "化学" in string:
        if code != "":
            code += "2"
            return code
        else:
            code = "2,"
    if "生物" in string:
        if code != "":
            code += "3"
            return code
        else:
            code = "3,"
    if "历史" in string:
        if code != "":
            code += "4"
            return code
        else:
            code = "4,"
    if "地理" in string:
        if code != "":
            code += "5"
            return code
        else:
            code = "5,"
    if "思想政治" in string:
        if code != "":
            code += "6"
            return code
        else:
            return "-1"


def choice_code_1(string):  # 单科必选
    if "物理" in string:
        return "1"
    elif "化学" in string:
        return "2"
    elif "生物" in string:
        return "3"
    elif "历史" in string:
        return "4"
    elif "地理" in string:
        return "5"
    elif "思想政治" in string:
        return "6"


def excel_table():
    name_of_shi = []
    add_shi(name_of_shi)
    province_and_shi_names = []
    province_and_shi(province_and_shi_names)
    data_book = xlrd.open_workbook("all_schools//data.xls", formatting_info=True)
    data_book = copy(data_book)
    data_sheet = data_book.get_sheet(0)

    f_name = open("profile//school_name.txt", "r", encoding="utf8")
    f_error = open("profile//error.txt", "a")
    f_ok = open("profile//ok.txt", "w")
    name = f_name.readline().replace("\n", "")
    index = 1
    while name:
        path = "all_schools\\" + name + ".xls"
        try:
            book = xlrd.open_workbook(path)
            sheet = book.sheet_by_index(0)
            f_ok.write(name + "\n")
        except:
            name = f_name.readline().replace("\n", "")
            continue
        index = insert_data(data_book, sheet, data_sheet, name_of_shi, province_and_shi_names, index)
        name = f_name.readline().replace("\n", "")
    data_book.save("all_schools//data.xls")


def PutDataToMysql(cursor,database):
    cursor.execute("drop table if exists tb_volunteer_2021")
    cursor.execute("CREATE TABLE if not exists tb_volunteer_2021 (id bigint auto_increment not null comment '主键', name varchar(11) not null default '' comment '高校名称', university_code varchar(11) not null default '' comment '高校代码', province varchar(20) not null default '' comment '所在省份', city varchar(20) not null default '' comment '所在市', address varchar(101) not null default '' comment '详细地址', is_undergraduate_school tinyint(3) not null default 0 comment '1表示是本科否则0', is_junior_school tinyint(3) not null default 0 comment '1表示专科否则0', is_985 tinyint(3) not null default 0 comment '1表示是985否则0', is_211 tinyint(3) not null default 0  comment '1表示211否则0', is_public tinyint(3) not null default 0 comment '1表示公办否则0', is_private tinyint(3) not null default 0 comment '1表示民办否则0', category varchar(11) not null default '' comment '学校分类，理工/综合/师范', pic_link varchar(601) not null default '' comment '校徽链接', employment_rate tinyint(3) not null default 0 comment '就业率', abroad_rate tinyint(3) not null default 0 comment '出国率', further_rate tinyint(3) not null default 0 comment '考研率', volunteer_section tinyint(3) not null default 0 comment '录取批次', lowest_score int not null default 0 comment '录取最低分', lowest_position int not null default 0 comment '录取最低位次', professional_name varchar(255) not null default '' comment '专业名称', subject_restriction_type tinyint(3) not null default 0 comment '选科要求类型', subject_restriction_detail varchar(20) not null default '' comment '具体选科要求', score int not null default 0 comment '专业最低分', position bigint(20) not null default 0 comment '专业最低位次', enrollment int not null default 0 comment '录取人数', time tinyint(3) not null default 0 comment '学制', fee int not null default 0 comment '学费', double_first_class_subject_number int not null default 0 comment '双一流学科数', country_specific_subject_number int not null default 0 comment '国家特色专业数量', master_point int not null default 0 comment '硕士点', doctor_point int not null default 0 comment '博士点',primary key (id)) engine = InnoDB default charset = utf8mb4 comment '用户志愿表'")
    cursor.execute("alter table tb_volunteer convert to character set utf8")

    length = 1
    path = "all_schools//data.xls"
    book = xlrd.open_workbook(path)
    sheet = book.sheet_by_index(0)

    while True:
        try:
            if sheet.cell(length + 1, 0).value != "":
                length += 1
            else:
                break
        except:
            break

    value = ()
    for i in range(1, length):
        value = ()
        for j in range(1, 32):
            value += (sheet[i][j].value,)
        sql = "insert into tb_volunteer_2021 (name,university_code,province,city,address,is_undergraduate_school,is_junior_school,is_985,is_211,is_public,is_private,category,pic_link,employment_rate,abroad_rate,further_rate,volunteer_section,lowest_score,lowest_position,professional_name,subject_restriction_type,subject_restriction_detail,score,position,enrollment,time,fee,double_first_class_subject_number,country_specific_subject_number,master_point,doctor_point)VALUES(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
        cursor.execute(sql, value)
        database.commit()















def Index(request):
    # 连接数据库 创建爬虫的记录表
    database = connect_mysql()
    cursor = database.cursor()  # 获取游标
    create_table(cursor)
    # 写入初始状态
    start_time = time.time()
    spider_start(start_time, cursor, database)
    # 配置selenium
    chrome_options = Options()
    # chrome_options.add_argument('--headless')
    chrome_options.add_argument('--disable-gpu')
    chrome_options.add_experimental_option('excludeSwitches', ['enable_automation'])
    driver = webdriver.Chrome(chrome_options=chrome_options)

    f = open("profile/school_names.txt", "r")  # 学校名称的url编码
    error_f = open("profile/error.txt", "a")
    f_comparision_table = open("profile/学校名称对照表.txt", "a")
    line = f.readline()
    c = 1
    while line:

        try:
            baseurl = get_baseurl(line)  # 返回首页网址
            datalist = []
            get_page_pre(driver, baseurl, datalist)  # 爬取概览页内容
            # 借用上一个页面
            driver.find_element_by_xpath(
                "/html/body/div/article/section/div[1]/div[1]/div/div[2]/div/div[2]/div[2]/div[1]/ul/li[1]/div/div[1]/a").click()
            time.sleep(0.5)
            set_province_first(driver)
            set_province_second(driver)
            set_year_first(driver)
            set_year_second(driver)
            flag1 = set_type_first(driver, "普通类一段")
            if flag1 == True:
                get_page_score(driver, datalist)
            else:
                datalist.append("")  # 13
                datalist.append("")  # 14
                datalist.append("")  # 15
            flag2 = set_type_first(driver, "普通类二段")
            if flag2 == True:  # False直接赋为空
                get_page_score(driver, datalist)
            else:
                datalist.append("")  # 16
                datalist.append("")  # 17
                datalist.append("")  # 18
            # 爬取招生页
            driver.find_element_by_xpath("/html/body/div[2]/div[2]/div[2]/div/ul/li[4]").click()
            time.sleep(0.5)
            set_province_ad(driver)
            set_year_ad(driver)
            if flag1 == True:
                set_type_ad(driver, "普通类一段")
                get_page_ad(driver, datalist)
            else:
                datalist.append("")  # 19
            if flag2 == True:
                set_type_ad(driver, "普通类二段")
                get_page_ad(driver, datalist)
            else:
                datalist.append("")  # 120
            # 爬取专业页
            driver.find_element_by_xpath("/html/body/div[2]/div[2]/div[2]/div/ul/li[3]").click()
            time.sleep(0.1)
            get_page_special(driver, datalist)
            # 整合数据
            try:
                integration(datalist)
            except:
                print("error")
            savedata(datalist[0], datalist)
            f_comparision_table.write(str(datalist[0]) + '\n')
            print(datalist[0])
            print(c)
            c += 1
            close_driver(driver)
        except Exception as e:
            print(e)
            error_f.write(str(line) + '\n')
            error_f.close()
            error_f = open("profile//error.txt", "a")
        line = f.readline()
        # time.sleep(10)
    error_f.close()
    f.close()
    f_comparision_table.close()
    # 爬虫运行完毕   写入结束状态
    end_time = time.time()
    spider_end(end_time, cursor, database)
    # 发出邮件
    excel_table()
    Send_mail()
    PutDataToMysql(cursor,database)


if __name__=="__main__":
    Index(0)
