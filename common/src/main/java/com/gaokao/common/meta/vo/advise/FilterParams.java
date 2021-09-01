package com.gaokao.common.meta.vo.advise;

import lombok.Data;

import java.util.List;

/**
 * @author MaeYon-Z
 * date 2021-08-28
 */
@Data
public class FilterParams {

    //成绩
    private Integer score;

    //选课信息
    private String subject;

    //其他条件
    private String other;
}
