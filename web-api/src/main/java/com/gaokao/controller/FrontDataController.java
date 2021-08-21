package com.gaokao.controller;

import com.gaokao.common.meta.AjaxResult;
import com.gaokao.common.meta.vo.frontdata.RegionVO;
import com.gaokao.common.service.FrontDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MaeYon-Z
 * date 2021-08-21
 */
@Slf4j
@RestController
@RequestMapping("/xhr/v1/getData")
public class FrontDataController {
    @Autowired
    private FrontDataService frontDataService;

    @RequestMapping("/regions")
    public AjaxResult<List<RegionVO>> getRegions(){
        List<String> provincesList = frontDataService.getProvinces();
        List<RegionVO> regionVOS = new ArrayList<>();
        List<String> citiesList = new ArrayList<>();
        for (String provinceName:provincesList) {
            RegionVO regionVO = new RegionVO();
            citiesList = frontDataService.getCities(provinceName);
            regionVO.setProvince(provinceName);
            regionVO.setCities(citiesList);
            regionVOS.add(regionVO);
        }
        return AjaxResult.SUCCESS(regionVOS);
    }

}
