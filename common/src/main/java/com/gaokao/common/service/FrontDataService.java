package com.gaokao.common.service;

import java.util.List;

/**
 * @author MaeYon-Z
 * date 2021-08-21
 */
public interface FrontDataService {
    List<String> getProvinces();
    List<String> getCities(String provinceName);
}
