package com.gaokao.controller;

import com.gaokao.common.constants.VolunteerConstant;
import com.gaokao.common.meta.AjaxResult;
import com.gaokao.common.meta.vo.volunteer.*;
import com.gaokao.common.service.VolunteerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 查询某一用户当前的志愿表
     * 这个类型不是null，后期会改
     */
    @GetMapping("/getCurrent/{id}")
    public AjaxResult<UserFormDetailVO> listCurrent(@PathVariable Long id) {
        return AjaxResult.SUCCESS(volunteerService.listCurrentForm(id));
    }

    /**
     * 查询某一用户所有的志愿表
     *
     * @param id
     * @return
     */
    @GetMapping("/getAll/{id}")
    public AjaxResult<List<UserFormAllVO>> listAllForm(@PathVariable Long id) {
        return AjaxResult.SUCCESS(volunteerService.listAll(id));
    }

    @PostMapping("/changeCurrentForm")
    public AjaxResult<Long> changeCurrentForm(ChangeCurrentFormParams changeCurrentFormParams) {
        return AjaxResult.SUCCESS(volunteerService.changeCurrentForm(changeCurrentFormParams.getUserId(),
                changeCurrentFormParams.getPreFormId(),
                changeCurrentFormParams.getNewFormId()));
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
                volunteerCreateParams.getScore(),
                volunteerCreateParams.getGeneratedType(),
                volunteerCreateParams.getName());

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

        Long result = volunteerService.updateUserFormName(volunteerUpdateParams.getUserId(),
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
                addVolunteerParams.getSection(),
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
                deleteVolunteerParams.getSection(),
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
                swapVolunteerParams.getSection(),
                swapVolunteerParams.getFirstVolunteerPosition(),
                swapVolunteerParams.getSecondVolunteerId());
        Long result = volunteerService.setVolunteer(swapVolunteerParams.getUserId(),
                swapVolunteerParams.getFormId(),
                swapVolunteerParams.getSection(),
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
                upVolunteerParams.getSection(),
                upVolunteerParams.getVolunteerPosition(),
                VolunteerConstant.EMPTY_VOLUNTEER);
        Long result = volunteerService.setVolunteer(upVolunteerParams.getUserId(),
                upVolunteerParams.getFormId(),
                upVolunteerParams.getSection(),
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
                downVolunteerParams.getSection(),
                downVolunteerParams.getVolunteerPosition(),
                VolunteerConstant.EMPTY_VOLUNTEER);
        Long result = volunteerService.setVolunteer(downVolunteerParams.getVolunteerId(),
                downVolunteerParams.getFormId(),
                downVolunteerParams.getSection(),
                downVolunteerParams.getVolunteerPosition() + 1,
                downVolunteerParams.getVolunteerId());

        if (result == -1L)
            return AjaxResult.FAIL("操作失败");
        else
            return AjaxResult.SUCCESS(result);

    }

}
