package com.gaokao.common.meta.vo.advise;

import lombok.Data;

/**
 * @author MaeYon-Z
 * date 2021-08-10
 */
@Data
public class AdviseVO {
    /*
    * 学校名
    * */
    private String universityName;

    /*
    * 学校代码
    * */
    private String universityCode;

    /*
    * 专业名
    * */
    private String majorName;

    /*
    * 专业代码
    * */
    private String majorCode;

    /*
    * 预估最低位次
    * */
    private int guessRank;

    /*
    * 录取几率
    * */
    private int rate;
}
