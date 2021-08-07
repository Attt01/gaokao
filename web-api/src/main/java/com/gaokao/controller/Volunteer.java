package com.gaokao.controller;

import com.gaokao.common.meta.AjaxResult;
import com.gaokao.common.meta.vo.volunteer.*;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.web.bind.annotation.*;

/**
 * @author attack204
 * date:  2021/8/7
 * email: 757394026@qq.com
 */
@Slf4j
@RestController
@RequestMapping("/xhr/v1/volunteer")
public class Volunteer {

    /**
     * 查询某一用户的志愿表
     * 这个类型不是null，后期会改
     */
    @GetMapping("/{id}")
    public AjaxResult<Long> list(@PathVariable Long id) {
        return null;
    }

    /**
     * 创建志愿表
     * @return 返回新创建的志愿表id
     */
    @PostMapping("/create")
    public AjaxResult<Long> create(VolunteerCreateParams volunteerCreateParams) {
        return null;
    }

    /**
     * 修改志愿表名称
     */
    @PostMapping("/updateName")
    public AjaxResult<Long> updateName(VolunteerUpdateParams volunteerUpdateParams) {
        return null;
    }

    /**
     * 添加志愿
     */
    @PostMapping("/addVolunteer")
    public AjaxResult<Long> addVolunteer(AddVolunteerParams addVolunteerParams) {
        return null;
    }

    /**
     * 删除志愿
     */
    @PostMapping("/deleteVolunteer")
    public AjaxResult<Long> deleteVolunteer(DeleteVolunteerParams deleteVolunteerParams) {
        return null;
    }

    /**
     * 志愿换位
     */
    @PostMapping("/swapVolunteer")
    public AjaxResult<Long> swapVolunteer(SwapVolunteerParams swapVolunteerParams) {
        return null;
    }

    /**
     * 上调志愿
     * 本质是和上一个交换位置
     */
    @PostMapping("/upVolunteer")
    public AjaxResult<Long> upVolunteer(UpVolunteerParams upVolunteerParams) {
        return null;
    }

    /**
     * 下调志愿
     * 本质是和下一个交换位置
     */
    @PostMapping("/downVolunteer")
    public AjaxResult<Long> downVolunteer(DownVolunteerParams downVolunteerParams) {
        return null;
    }

}
