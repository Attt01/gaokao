package com.gaokao.common.service;

import com.gaokao.common.meta.vo.frontdata.BatchVO;
import com.gaokao.common.meta.vo.frontdata.FrontDataVO;

import java.util.List;

/**
 * @author MaeYon-Z
 * date 2021-08-21
 */
public interface FrontDataService {
    List<BatchVO> getBatches();

    List<FrontDataVO> getFrontData(Integer flag);
}
