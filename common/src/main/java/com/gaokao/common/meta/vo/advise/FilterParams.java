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

    /**
     * 其他信息，包括填报批次、就读地区、大学类型、专业类型
     */
    private List<Integer> other;

    private String universityName;

    private String majorName;

    private Integer type;
}
