package com.gaokao.controller;

import com.gaokao.common.meta.AjaxResult;
import com.gaokao.common.service.SpiderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author attack204
 * date:  2021/9/24
 * email: 757394026@qq.com
 */
@Slf4j
@RestController
@RequestMapping("/xhr/v1")
public class SpiderController {

    @Autowired
    SpiderService spiderService;

    @RequestMapping("/startMission")
    public AjaxResult<Long> startSpiderMission() {

        Long result = spiderService.startSpiderMission();

        if(result != -1L) {
            return AjaxResult.SUCCESS(result);
        } else {
            return AjaxResult.FAIL("启动爬虫任务失败，请检查配置或重试");
        }

    }

}
