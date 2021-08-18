package com.gaokao.common.meta.po;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author attack204
 * date:  2021/8/18
 * email: 757394026@qq.com
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "tb_volunteer")
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String universityCode;

    private String province;

    private String address;

    @Column(name = "is_undergraduate_school")
    private Long undergraduateSchoolIsOrNot;

    @Column(name = "is_junior_school")
    private Long juniorSchoolIsOrNot;

    @Column(name = "is_985")
    private Long is985;

    @Column(name = "is_211")
    private Long is211;

    @Column(name = "is_public")
    private Long PublicIsOrNot;

    @Column(name = "is_private")
    private Long PrivateIsOrNot;

    private String category;

    private String picLink;

    private String employmentRate;

    private String abroadRate;

    private String furtherRate;

    private Integer lowestScoreOne;

    private Integer lowestPositionOne;

    private String professionalNameOne;

    private Integer subjectRestrictionTypeOne;

    private String subjectRestrictionDetailOne;

    private String majorCodeOne;

    private Integer scoreOne;

    private Integer positionOne;

    private Integer enrollmentOne;

    private Integer timeOne;

    private Integer feeOne;


    private Integer lowestScoreTwo;

    private Integer lowestPositionTwo;

    private String professionalNameTwo;

    private Integer subjectRestrictionTypeTwo;

    private String subjectRestrictionDetailTwo;

    private String majorCodeTwo;

    private Integer scoreTwo;

    private Integer positionTwo;

    private Integer enrollmentTwo;

    private Integer timeTwo;

    private Integer feeTwo;

    private Integer doubleFirstClassSubjectNumber;

    private Integer countrySpecificSubjectNumber;

    private Integer masterPoint;

    private Integer doctorPoint;
}
