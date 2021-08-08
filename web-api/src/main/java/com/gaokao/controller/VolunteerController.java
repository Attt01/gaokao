package com.gaokao.controller;

import com.gaokao.common.constants.VolunteerConstant;
import com.gaokao.common.meta.AjaxResult;
import com.gaokao.common.meta.po.VolunteerForm;
import com.gaokao.common.meta.vo.volunteer.*;
import com.gaokao.common.service.VolunteerService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author attack204
 * date:  2021/8/7
 * email: 757394026@qq.com
 */
@Slf4j
@RestController
@RequestMapping("/xhr/v1/volunteer")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    /**
     * 查询某一用户的志愿表
     * 这个类型不是null，后期会改
     */
    @GetMapping("/{id}")
    public AjaxResult<List<VolunteerFormVO>> list(@PathVariable Long id) {
        return AjaxResult.SUCCESS(volunteerService.list(id));
    }

    /**
     * 创建志愿表
     *
     * @return 返回新创建的志愿表id
     */
    @PostMapping("/create")
    public AjaxResult<Long> create(VolunteerCreateParams volunteerCreateParams) {

        Long result = volunteerService.create(volunteerCreateParams.getUserId(),
                volunteerCreateParams.getSubject(),
                volunteerCreateParams.getScore());

        if (result == -1L)
            return AjaxResult.FAIL("创建失败");
        else
            return AjaxResult.SUCCESS(result);

    }

    /**
     * 修改志愿表名称
     */
    @PostMapping("/updateName")
    public AjaxResult<Long> updateName(VolunteerUpdateParams volunteerUpdateParams) {

        Long result = volunteerService.updateVolunteerFormName(volunteerUpdateParams.getUserId(),
                volunteerUpdateParams.getFormId(),
                volunteerUpdateParams.getName());

        if (result == -1L)
            return AjaxResult.FAIL("更新失败");
        else
            return AjaxResult.SUCCESS(result);

    }

    /**
     * 添加志愿
     */
    @PostMapping("/addVolunteer")
    public AjaxResult<Long> addVolunteer(AddVolunteerParams addVolunteerParams) {
        Long result = volunteerService.setVolunteer(addVolunteerParams.getUserId(),
                addVolunteerParams.getFormId(),
                addVolunteerParams.getVolunteerPosition(),
                addVolunteerParams.getVolunteerId());

        if (result == -1L)
            return AjaxResult.FAIL("操作失败");
        else
            return AjaxResult.SUCCESS(result);
    }

    /**
     * 删除志愿
     */
    @PostMapping("/deleteVolunteer")
    public AjaxResult<Long> deleteVolunteer(DeleteVolunteerParams deleteVolunteerParams) {
        Long result = volunteerService.setVolunteer(deleteVolunteerParams.getUserId(),
                deleteVolunteerParams.getFormId(),
                deleteVolunteerParams.getVolunteerPosition(),
                VolunteerConstant.EMPTY_VOLUNTEER);
        if (result == -1L)
            return AjaxResult.FAIL("操作失败");
        else
            return AjaxResult.SUCCESS(result);
    }

    /**
     * 志愿换位
     */
    @PostMapping("/swapVolunteer")
    public AjaxResult<Long> swapVolunteer(SwapVolunteerParams swapVolunteerParams) {
        volunteerService.setVolunteer(swapVolunteerParams.getUserId(),
                swapVolunteerParams.getFormId(),
                swapVolunteerParams.getFirstVolunteerPosition(),
                swapVolunteerParams.getSecondVolunteerId());
        Long result = volunteerService.setVolunteer(swapVolunteerParams.getUserId(),
                swapVolunteerParams.getFormId(),
                swapVolunteerParams.getSecondVolunteerPosition(),
                swapVolunteerParams.getFirstVolunteerId());

        if (result == -1L)
            return AjaxResult.FAIL("操作失败");
        else
            return AjaxResult.SUCCESS(result);

    }

    /**
     * 上调志愿
     * 本质是和上一个交换位置
     */
    @PostMapping("/upVolunteer")
    public AjaxResult<Long> upVolunteer(UpVolunteerParams upVolunteerParams) {
        volunteerService.setVolunteer(upVolunteerParams.getUserId(),
                upVolunteerParams.getFormId(),
                upVolunteerParams.getVolunteerPosition(),
                VolunteerConstant.EMPTY_VOLUNTEER);
        Long result = volunteerService.setVolunteer(upVolunteerParams.getUserId(),
                upVolunteerParams.getFormId(),
                upVolunteerParams.getVolunteerPosition() - 1,
                upVolunteerParams.getVolunteerId());

        if (result == -1L)
            return AjaxResult.FAIL("操作失败");
        else
            return AjaxResult.SUCCESS(result);

    }

    /**
     * 下调志愿
     * 本质是和下一个交换位置
     */
    @PostMapping("/downVolunteer")
    public AjaxResult<Long> downVolunteer(DownVolunteerParams downVolunteerParams) {
        volunteerService.setVolunteer(downVolunteerParams.getUserId(),
                downVolunteerParams.getFormId(),
                downVolunteerParams.getVolunteerPosition(),
                VolunteerConstant.EMPTY_VOLUNTEER);
        Long result = volunteerService.setVolunteer(downVolunteerParams.getVolunteerId(),
                downVolunteerParams.getFormId(),
                downVolunteerParams.getVolunteerPosition() + 1,
                downVolunteerParams.getVolunteerId());

        if (result == -1L)
            return AjaxResult.FAIL("操作失败");
        else
            return AjaxResult.SUCCESS(result);

    }

}
