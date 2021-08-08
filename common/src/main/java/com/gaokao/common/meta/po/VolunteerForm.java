package com.gaokao.common.meta.po;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author attack204
 * date:  2021/8/8
 * email: 757394026@qq.com
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "tb_volunteer_form")
public class VolunteerForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String name;

    private Long score;

    private String subject;

    private String volunteerList;

    private Long generatedType;

    @Column(name = "is_current")
    private Long currentIsOrNot;

    private Long generatedTime;


    /*
        id             bigint auto_increment not null comment '主键',
    user_id        bigint      not null default 0 comment '用户Id',
    name           varchar(20) not null default '' comment '志愿表名称',
    score          int         not null default 0 comment '用户分数',
    subject        varchar(10) not null default '' comment '用户的选课信息',
    volunteer_list text comment '志愿id。[2, 0, 0, 66];表示第一个志愿的id是2，第4个志愿的id是66',
    generated_type tinyint     not null default 0 comment '生成类型，默认是手动生成',
    is_current     tinyint     not null default 0 comment '是否为当前表',
    generated_time bigint      not null default 0 comment '生成时间',
     */
}
