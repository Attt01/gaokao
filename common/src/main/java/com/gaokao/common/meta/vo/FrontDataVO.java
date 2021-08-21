package com.gaokao.common.meta.vo;

import lombok.Data;

import java.util.List;

/**
 * @author MaeYon-Z
 * date 2021-08-21
 */
@Data
public class FrontDataVO {

    private List<String> secondValues;

    private List<String> thirdValues;

    public void setSecondValues(List<String> secondValues) {
        this.secondValues = secondValues;
    }

    public void setThirdValues(List<String> thirdValues) {
        this.thirdValues = thirdValues;
    }
}
