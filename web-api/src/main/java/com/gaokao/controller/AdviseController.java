package com.gaokao.controller;

import com.gaokao.common.meta.AjaxResult;
import com.gaokao.common.meta.vo.advise.AutoGenerateFormParams;
import com.gaokao.common.meta.vo.advise.FilterParams;
import com.gaokao.common.meta.vo.advise.AdviseVO;
import com.gaokao.common.meta.vo.volunteer.UserFormDetailVO;
import com.gaokao.common.service.AdviseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author MaeYon-Z
 * date 2021-08-09
 */
@Slf4j
@RestController
@RequestMapping("/xhr/v1/advise")
public class AdviseController {

    @Autowired
    private AdviseService adviseService;

    @GetMapping("/getrank/{score}")
    public AjaxResult<Integer> getRank(@PathVariable Integer score){
        return AjaxResult.SUCCESS(adviseService.getUserRank(score));
    }

    @GetMapping("/listall")
    public AjaxResult<Page<AdviseVO>> advise(@ModelAttribute FilterParams filterParams){
       return AjaxResult.SUCCESS(adviseService.list(filterParams));
    }

    @GetMapping("autoGenerateForm")
    public AjaxResult<UserFormDetailVO> autoGenerateVolunteerForm(@ModelAttribute AutoGenerateFormParams autoGenerateFormParams){
        return AjaxResult.SUCCESS(adviseService.generateVoluntForm(autoGenerateFormParams));
    }

}
