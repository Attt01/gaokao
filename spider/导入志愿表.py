# -!- coding: utf-8 -!-
import time

import pymysql
import xlrd

def main():
    #连接数据库
    database = connect_mysql()
    cursor = database.cursor()
    create_table(cursor)
    f_name = open("d://school_name.txt", "r", encoding="utf8")
    f_error = open("d://error.txt", "a")
    f_ok = open("d://ok.txt", "w")
    name = f_name.readline().replace("\n","")
    index = 1
    while name:
        path = "D:\\kuake_数据\\school\\school - 副本（1）\\哈尔滨工业大学（威海）.xls"
        try:
            book = xlrd.open_workbook(path)
            sheet = book.sheet_by_index(0)
            f_ok.write(name+"\n")
        except:
            f_error.write(name+"\n")
            name = f_name.readline().replace("\n","")
            continue
        index = insert_data(cursor,sheet,database)
        exit(0)
        name = f_name.readline().replace("\n","")





def insert_data(cursor,sheet,database):
    len_one = 0
    len_two = 0
    while True:
        try:
            if sheet.cell(len_one+1,14).value != "":
                len_one += 1
            else:
                break
        except:
            break
    while True:
        try:
            if sheet.cell(len_two+1,22).value != "":
                len_two += 1
            else:
                break
        except:
            break

    for i in range(len_one):#遍历一段专业
        value = ()
        value += (sheet.cell(1,0).value,)#学校名字2
        value += (sheet.cell(1,1).value,)#学校代码3
        # value += (sheet.cell(1,3).value,)#所在省份4
        if sheet.cell(1,3).value == "-2" or sheet.cell(1,3).value == "-1":
            info = sheet.cell(1,7).value
            province_list=['上海', '海南', '北京', '天津', '湖南', '湖北', '江苏', '福建', '广东', '河北', '辽宁', '重庆', '山东', '浙江', '河南', '四川', '安徽', '广西', '贵州', '江西', '云南', '山西', '陕西', '甘肃', '新疆', '黑龙江', '内蒙古', '吉林', '宁夏', '青海', '西藏']
            flag =False
            for item in province_list:
                if item in info:
                    value += (item,)
                    flag = True
                    break
            if flag == False:
                value += ("-1",)
        else:
            value += (sheet.cell(1, 3).value,)
        value += (sheet.cell(1,7).value,)#详细地址5
        is_undergraduate_school = 0
        if "本科" in sheet.cell(1,5).value:
            is_undergraduate_school = 1
        else:
            is_undergraduate_school = 0
        value += (is_undergraduate_school,)#是不是本科6
        is_junior_school = 0
        if "专科" in sheet.cell(1,5).value:
            is_junior_school = 1
        else:
            is_junior_school = 0
        value += (is_junior_school,)#是不是专科7
        is_985 = 0
        if "985" in sheet.cell(1,5).value:
            is_985 = 1
        else:
            is_985 = 0
        value += (is_985,)#是不是985 8
        is_211 = 0
        if "985" in sheet.cell(1,5).value or "211" in sheet.cell(i,6).value:
            is_211 = 1
        else:
            is_211 = 0
        value += (is_211,)#是不是211 9
        is_public = 0
        if "公办" in sheet.cell(1,5).value:
            is_public = 1
        else:
            is_public = 0
        value += (is_public,)#是不是公办 10
        is_private = 0
        if "民办" in sheet.cell(1,5).value:
            is_private = 1
        else:
            is_private = 0
        value += (is_private,)  # 是不是民办11
        value += (sheet.cell(1,2).value,)#院校类型12
        value += (sheet.cell(1,4).value,)#校徽链接13
        if sheet.cell(1,8).value != "":
            value += (int(sheet.cell(1,8).value.replace("%","")),)#就业率14
        else:
            value += (-1,)
        if sheet.cell(1,9).value != "":
            value += (int(sheet.cell(1,9).value.replace("%","")),)#出国率15
        else:
            value += (-1,)
        if sheet.cell(1,10).value != "":
            value += (int(sheet.cell(1,10).value.replace("%","")),)#考研率16
        else:
            value += (-1,)
        value += (1,)#专业类型17
        if sheet.cell(1,11).value != "":
            value += (int(sheet.cell(1,11).value),)#一段最低分18
        else:
            value += (-1,)
        if sheet.cell(1,12).value != "":
            value += (int(sheet.cell(1,12).value),)#一段最低位次19
        else:
            value += (-1,)
        value += (sheet.cell(i+1,13).value,)#专业名称20
        res = judge_type(sheet.cell(i+1,14).value)
        value += (int(res[0]),)#选科要求类型21
        value += (res[1],)#具体要求科目22

        if sheet.cell(i+1,15).value != "":
            try:
                value += (int(sheet.cell(i+1,15).value),)#专业最低分23
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(i+1,16).value != "":
            value += (int(sheet.cell(i+1,16).value),)#专业最低位次24
        else:
            value += (-1,)
        if sheet.cell(i+1,17).value != "":
            try:
                value += (int(sheet.cell(i+1,17).value),)#录取人数25
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(i+1,18).value != "":
            value +=(int(sheet.cell(i+1,18).value),)#学制26
        else:
            value += (-1,)
        if sheet.cell(i+1,19).value != "":
            try:
                value +=(int(sheet.cell(i+1,19).value),)#学费27
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(1,29).value != "":
            value += (int(sheet.cell(1,29).value),)#双一流28
        else:
            value += (-1,)
        if sheet.cell(1,30).value != "":
            value += (int(sheet.cell(1, 30).value),)#国家特色29
        else:
            value += (-1,)
        if sheet.cell(1,31).value != "":
            value += (int(sheet.cell(1, 31).value),)#硕士点30
        else:
            value += (-1,)
        if sheet.cell(1,32).value != "":
            value += (int(sheet.cell(1, 32).value),)#博士点31
        else:
            value += (-1,)
        sql = "insert into tb_volunteer (name,university_code,province,address,is_undergraduate_school,is_junior_school,is_985,is_211,is_public,is_private,category,pic_link,employment_rate,abroad_rate,further_rate,volunteer_section,lowest_score,lowest_position,professional_name,subject_restriction_type,subject_restriction_detail,score,position,enrollment,time,fee,double_first_class_subject_number,country_specific_subject_number,master_point,doctor_point)VALUES(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
        cursor.execute(sql,value)
        database.commit()

    for i in range(len_two):#遍历二段专业
        value = ()
        value += (sheet.cell(1,0).value,)#学校名字2
        value += (sheet.cell(1,1).value,)#学校代码3
        # value += (sheet.cell(1,3).value,)#所在省份4
        if sheet.cell(1,3).value == "-2" or sheet.cell(1,3).value == "-1":
            info = sheet.cell(1,7).value
            province_list=['上海', '海南', '北京', '天津', '湖南', '湖北', '江苏', '福建', '广东', '河北', '辽宁', '重庆', '山东', '浙江', '河南', '四川', '安徽', '广西', '贵州', '江西', '云南', '山西', '陕西', '甘肃', '新疆', '黑龙江', '内蒙古', '吉林', '宁夏', '青海', '西藏']
            flag =False
            for item in province_list:
                if item in info:
                    value += (item,)
                    flag = True
                    break
            if flag == False:
                value += ("-1",)
        else:
            value += (sheet.cell(1, 3).value,)
        value += (sheet.cell(1,7).value,)#详细地址5
        is_undergraduate_school = 0
        if "本科" in sheet.cell(1,5).value:
            is_undergraduate_school = 1
        else:
            is_undergraduate_school = 0
        value += (is_undergraduate_school,)#是不是本科6
        is_junior_school = 0
        if "专科" in sheet.cell(1,5).value:
            is_junior_school = 1
        else:
            is_junior_school = 0
        value += (is_junior_school,)#是不是专科7
        is_985 = 0
        if "985" in sheet.cell(1,5).value:
            is_985 = 1
        else:
            is_985 = 0
        value += (is_985,)#是不是985 8
        is_211 = 0
        if "985" in sheet.cell(1,5).value or "211" in sheet.cell(i,6).value:
            is_211 = 1
        else:
            is_211 = 0
        value += (is_211,)#是不是211 9
        is_public = 0
        if "公办" in sheet.cell(1,5).value:
            is_public = 1
        else:
            is_public = 0
        value += (is_public,)#是不是公办 10
        is_private = 0
        if "民办" in sheet.cell(1,5).value:
            is_private = 1
        else:
            is_private = 0
        value += (is_private,)  # 是不是民办11
        value += (sheet.cell(1,2).value,)#院校类型12
        value += (sheet.cell(1,4).value,)#校徽链接13
        if sheet.cell(1,8).value != "":
            value += (int(sheet.cell(1,8).value.replace("%","")),)#就业率14
        else:
            value += (-1,)
        if sheet.cell(1,9).value != "":
            value += (int(sheet.cell(1,9).value.replace("%","")),)#出国率15
        else:
            value += (-1,)
        if sheet.cell(1,10).value != "":
            value += (int(sheet.cell(1,10).value.replace("%","")),)#考研率16
        else:
            value += (-1,)
        value += (2,)#专业类型17
        if sheet.cell(1,20).value != "":
            value += (int(sheet.cell(1,20).value),)#二段最低分18
        else:
            value += (-1,)
        if sheet.cell(1,21).value != "":
            value += (int(sheet.cell(1,21).value),)#二段最低位次19
        else:
            value += (-1,)
        value += (sheet.cell(i+1,22).value,)#专业名称20
        res = judge_type(sheet.cell(i+1,23).value)
        value += (int(res[0]),)#选科要求类型21
        value += (res[1],)#具体要求科目22

        if sheet.cell(i+1,24).value != "":
            try:
                value += (int(sheet.cell(i+1,24).value),)#专业最低分23
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(i+1,25).value != "":
            value += (int(sheet.cell(i+1,25).value),)#专业最低位次24
        else:
            value += (-1,)
        if sheet.cell(i+1,26).value != "":
            try:
                value +=(int(sheet.cell(i+1,26).value),)#录取人数25
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(i+1,27).value != "":
            value +=(int(sheet.cell(i+1,27).value),)#学制26
        else:
            value += (-1,)
        if sheet.cell(i+1,28).value != "":
            try:
                value +=(int(sheet.cell(i+1,28).value),)#学费27
            except:
                value += (-1,)
        else:
            value += (-1,)
        if sheet.cell(1,29).value != "":
            value += (int(sheet.cell(1,29).value),)#双一流28
        else:
            value += (-1,)
        if sheet.cell(1,30).value != "":
            value += (int(sheet.cell(1, 30).value),)#国家特色29
        else:
            value += (-1,)
        if sheet.cell(1,31).value != "":
            value += (int(sheet.cell(1, 31).value),)#硕士点30
        else:
            value += (-1,)
        if sheet.cell(1,32).value != "":
            value += (int(sheet.cell(1, 32).value),)#博士点31
        else:
            value += (-1,)
        sql = "insert into tb_volunteer (name,university_code,province,address,is_undergraduate_school,is_junior_school,is_985,is_211,is_public,is_private,category,pic_link,employment_rate,abroad_rate,further_rate,volunteer_section,lowest_score,lowest_position,professional_name,subject_restriction_type,subject_restriction_detail,score,position,enrollment,time,fee,double_first_class_subject_number,country_specific_subject_number,master_point,doctor_point)VALUES(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
        cursor.execute(sql,value)
        database.commit()



def connect_mysql():
    return pymysql.connect(host="127.0.0.1", user="root", passwd="111111", charset="utf8", database="test")


def create_table(cursor):
    cursor.execute("CREATE TABLE tb_volunteer (id bigint auto_increment not null comment '主键', name varchar(11) not null default '' comment '高校名称', university_code varchar(11) not null default '' comment '高校代码', province varchar(20) not null default '' comment '所在省份', address varchar(101) not null default '' comment '详细地址', is_undergraduate_school tinyint(3) not null default 0 comment '1表示是本科否则0', is_junior_school tinyint(3) not null default 0 comment '1表示专科否则0', is_985 tinyint(3) not null default 0 comment '1表示是985否则0', is_211 tinyint(3) not null default 0  comment '1表示211否则0', is_public tinyint(3) not null default 0 comment '1表示公办否则0', is_private tinyint(3) not null default 0 comment '1表示民办否则0', category varchar(11) not null default '' comment '学校分类，理工/综合/师范', pic_link varchar(601) not null default '' comment '校徽链接', employment_rate tinyint(3) not null default 0 comment '就业率', abroad_rate tinyint(3) not null default 0 comment '出国率', further_rate tinyint(3) not null default 0 comment '考研率', volunteer_section tinyint(3) not null default 0 comment '录取批次', lowest_score int not null default 0 comment '录取最低分', lowest_position int not null default 0 comment '录取最低位次', professional_name varchar(255) not null default '' comment '专业名称', subject_restriction_type tinyint(3) not null default 0 comment '选科要求类型', subject_restriction_detail varchar(20) not null default '' comment '具体选科要求', score int not null default 0 comment '专业最低分', position bigint(20) not null default 0 comment '专业最低位次', enrollment int not null default 0 comment '录取人数', time tinyint(3) not null default 0 comment '学制', fee int not null default 0 comment '学费', double_first_class_subject_number int not null default 0 comment '双一流学科数', country_specific_subject_number int not null default 0 comment '国家特色专业数量', master_point int not null default 0 comment '硕士点', doctor_point int not null default 0 comment '博士点',primary key (id)) engine = InnoDB default charset = utf8mb4 comment '用户志愿表'")
    cursor.execute("alter table tb_volunteer convert to character set utf8")


def judge_type(string):
    if "(" in string:
        if "2科必选" in string:
            return "2",choice_code_2(string)
        elif "3科必选" in string:
            return "3",choice_code_356(string)
        elif "2选1" in string:
            return "4",choice_code_2(string)
        elif "3选1" in string:
            return "5",choice_code_356(string)
        elif "3选2" in string:
            return "6",choice_code_356(string)
        else:
            return "-1","-1"
    else:
        if "不限" in string:
            return "0","0"
        elif "必选" in string:
            return "1",choice_code_1(string)#单科必选
        else:
            return "-1","-1"


def choice_code_356(string):#3选1 三科必选  三选二
    flag = 0
    code = ""
    if "物" in string:
        code +="1;"
        flag +=1
    if "化" in string:
        code += "2;"
        flag +=1
    if "生" in string:
        if flag == 2:
            code += "3"
            return code
        else:
            code += "3;"
            flag += 1
    if "史" in string:
        if flag == 2:
            code += "4"
            return code
        else:
            code += "4;"
            flag += 1
    if "地" in string:
        if flag == 2:
            code += "5"
            return code
        else:
            code += "5;"
            flag += 1
    if "政" in string:
        if flag == 2:
            code += "6"
            return code
        else:
            return "-1"



def choice_code_2(string):#2选1
    code = ""
    if "物理" in string:
        code = "1;"
    if "化学" in string:
        if code != "":
            code = code + "2"
            return code
        else:
            code = "2;"
    if "生物" in string:
        if code != "":
            code = code + "3"
            return code
        else:
            code = "3;"
    if "历史" in string:
        if code != "":
            code = code + "4"
            return code
        else:
            code = "4;"
    if "地理" in string:
        if code != "":
            code = code + "5"
            return code
        else:
            code = "5;"
    if "思想政治" in string:
        code = code + "6"
        return code
    else:
        code = "-1"
        return code


def choice_code_2(string):#两科必选
    code = ""
    if "物理" in string:
        code += "1;"
    if "化学" in string:
        if code != "":
            code += "2"
            return code
        else:
            code = "2;"
    if "生物" in string:
        if code != "":
            code += "3"
            return code
        else:
            code = "3;"
    if "历史" in string:
        if code != "":
            code += "4;"
            return code
        else:
            code = "4;"
    if "地理" in string:
        if code != "":
            code += "5"
            return code
        else:
            code = "5;"
    if "思想政治" in string:
        if code != "":
            code += "6"
            return code
        else:
            return "-1"


def choice_code_1(string):#单科必选
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


if __name__=="__main__":
    main()