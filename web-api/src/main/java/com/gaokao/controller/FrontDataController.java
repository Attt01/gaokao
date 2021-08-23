package com.gaokao.controller;

import com.gaokao.common.meta.AjaxResult;
import com.gaokao.common.meta.vo.frontdata.BatchVO;
import com.gaokao.common.meta.vo.frontdata.FrontDataVO;
import com.gaokao.common.service.FrontDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/batch")
    public AjaxResult<List<BatchVO>> getBatches(){
        return AjaxResult.SUCCESS(frontDataService.getBatches());
    }

    @RequestMapping("/region")
    public AjaxResult<List<FrontDataVO>> getRegion(){
        return AjaxResult.SUCCESS(frontDataService.getFrontData(2));
    }

    @RequestMapping("/schooltype")
    public AjaxResult<List<FrontDataVO>> getSchoolType(){
        return AjaxResult.SUCCESS(frontDataService.getFrontData(3));
    }

    @RequestMapping("/majortype")
    public AjaxResult<List<FrontDataVO>> getMajorType(){
        return AjaxResult.SUCCESS(frontDataService.getFrontData(4));
    }

}
