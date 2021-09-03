package com.gaokao.common.meta.vo.advise;

import lombok.Data;

import java.util.List;

/**
 * @author MaeYon-Z
 * date 2021-08-28
 */
@Data
public class FilterParams {

    private Long userId;

    //成绩
    private Integer score;

    //选课信息
    private String subject;

    private Integer page;

    private Integer limit;

    private Integer total;

    private Integer level;          //录取批次：一段or二段

    private List<FilterParams1> location;       //地区

    private List<FilterParams1> classification; //学校类型

    private List<FilterParams1> majorType;      //专业类别

    private String universityName;

    private String majorName;

    private Integer type;
}
