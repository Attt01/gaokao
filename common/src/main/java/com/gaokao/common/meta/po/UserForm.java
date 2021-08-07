package com.gaokao.common.meta.po;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/*
* 用户志愿表
* */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "tb_user_form")
public class UserForm {
    /*
    * 主键
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /*
    * 用户id
    * */
    private long userId;

    /*
    * 志愿表id
    * */
    private int formId;

    /*
    * 志愿表项id，取值为1-96
    * */
    private int itemId;

    /*
    * 姓名
    * */
    private String userName;

    /*
    * 准考证号
    * */
    private String admissionNum;

    /*
    * 学校编码
    * */
    private String schoolId;

    /*
    * 学校名字
    * */
    private String schoolName;

    /*
    * 专业代码
    * */
    private String majorId;

    /*
    * 专业名字
    * */
    private String majorName;
}
