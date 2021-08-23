package com.gaokao.common.meta.vo.frontdata;

import lombok.Data;

import java.util.List;

/**
 * @author MaeYon-Z
 * date 2021-08-21
 */
@Data
public class FrontDataVO {
    private Integer value;

    private String label;

    private List<ThirdDataVO> children;

}
