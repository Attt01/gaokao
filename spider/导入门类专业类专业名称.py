# -!- coding: utf-8 -!-
import pymysql
import xlrd
def main():
    data_base = connect_mysql()
    cursor = data_base.cursor()
    create_table(cursor)
    path = "d://sub.xls"
    book = xlrd.open_workbook(path)
    sheet = book.sheet_by_index(0)
    j = 0
    for i in range(sheet.nrows):
        value = ()
        value += (sheet.cell(i+2,1).value,)#门类
        value += (sheet.cell(i+2,2).value,)#专业类
        value += (sheet.cell(i+2,4).value,)#专业名称
        sql = "insert into tb_subject (category,speciality_classification,subject_name)values(%s,%s,%s)"
        j += 1
        if j == 704:
            exit(0)
        cursor.execute(sql,value)
        data_base.commit()


def connect_mysql():
    return pymysql.connect(host="127.0.0.1", user="root", passwd="111111", charset="utf8", database="test")


def create_table(cursor):
    cursor.execute("drop table if exists tb_subject")
    cursor.execute("CREATE TABLE tb_subject (id bigint auto_increment not null comment '主键',category varchar(50) not null default '' comment '门类', speciality_classification varchar(50) not null default '' comment '专业类别', subject_name varchar(50) not null default '' comment '专业名称',primary key (id)) engine = InnoDB default charset = utf8mb4 comment '专业分类表'")
    cursor.execute("alter table tb_subject convert to character set utf8")

if __name__=="__main__":
    main()
