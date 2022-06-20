package com.gaokao.controller;

import com.gaokao.common.constants.VolunteerConstant;
import com.gaokao.common.meta.AjaxResult;
import com.gaokao.common.meta.po.FormVolunteer;
import com.gaokao.common.meta.vo.volunteer.*;
import com.gaokao.common.service.VolunteerService;
import com.gaokao.common.utils.UserUtils;
//import com.sun.org.apache.xpath.internal.operations.Bool;
import com.github.xiaolyuh.annotation.CachePut;
import com.github.xiaolyuh.annotation.Cacheable;
import com.github.xiaolyuh.annotation.FirstCache;
import com.github.xiaolyuh.annotation.SecondaryCache;
import com.github.xiaolyuh.support.CacheMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
    @GetMapping("/getCurrent/")
    @Cacheable(value = "user:info", depict = "用户信息缓存", cacheMode = CacheMode.ALL,
            firstCache = @FirstCache(expireTime = 4, timeUnit = TimeUnit.SECONDS),
            secondaryCache = @SecondaryCache(expireTime = 10, preloadTime = 3, forceRefresh = true, timeUnit = TimeUnit.SECONDS))
    public AjaxResult<UserFormDetailVO> listCurrent() {
        Long id = UserUtils.getUserId();
        UserFormDetailVO userFormDetailVO = volunteerService.listCurrentForm(id);
        if(userFormDetailVO == null) {
            return AjaxResult.FAIL("无法获取用户当前志愿表或用户未创建志愿表");
        } else {
            return AjaxResult.SUCCESS(volunteerService.listCurrentForm(id));
        }
    }

    /**
     * 查询某一用户所有的志愿表
     *
     * @return
     */
    @GetMapping("/getAll")
    public AjaxResult<List<UserFormAllVO>> listAllForm() {
        Long id = UserUtils.getUserId();
        return AjaxResult.SUCCESS(volunteerService.listAll(id));
    }

    @GetMapping("/delete/{id}")
    public AjaxResult<Long> deleteForm(@PathVariable Long id) {
        return AjaxResult.SUCCESS(volunteerService.deleteForm(id));
    }

    @PostMapping("/changeCurrentForm")
    public AjaxResult<Long> changeCurrentForm(@RequestBody ChangeCurrentFormParams changeCurrentFormParams) {
        return AjaxResult.SUCCESS(volunteerService.changeCurrentForm(UserUtils.getUserId(),
                changeCurrentFormParams.getNewFormId()));
    }

    /**
     * 创建志愿表
     *
     * @return 返回新创建的志愿表id
     */
    @PostMapping("/create")
    public AjaxResult<Long> create(@RequestBody VolunteerCreateParams volunteerCreateParams) {

        Long result = volunteerService.create(UserUtils.getUserId(),
                volunteerCreateParams.getSubject(),
                volunteerCreateParams.getScore(),
                volunteerCreateParams.getGeneratedType(),
                volunteerCreateParams.getName());

        if (result == -1L)
            return AjaxResult.FAIL("创建失败");
        else
            return AjaxResult.SUCCESS(result);

    }

    @GetMapping("/queryExist")
    public AjaxResult<Boolean> queryExist(@RequestBody VolunteerExistParams volunteerExistParams) {

        Boolean result = volunteerService.queryExist(volunteerExistParams.getFormId(),
                volunteerExistParams.getVolunteerSection(),
                volunteerExistParams.getVolunteerPosition());
        return AjaxResult.SUCCESS(result);

    }

    /**
     * 修改志愿表名称
     */
    @PostMapping("/updateName")
    public AjaxResult<Long> updateName(@RequestBody VolunteerUpdateParams volunteerUpdateParams) {

        Long result = volunteerService.updateUserFormName(UserUtils.getUserId(),
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
    public AjaxResult<Long> addVolunteer(@RequestBody AddVolunteerParams addVolunteerParams) {
        Long result = volunteerService.setVolunteer(UserUtils.getUserId(),
                addVolunteerParams.getFormId(),
                addVolunteerParams.getSection(),
                addVolunteerParams.getVolunteerPosition(),
                addVolunteerParams.getVolunteerId());

        if (result == -1L)
            return AjaxResult.FAIL("添加志愿失败或该志愿表下已有相同志愿");
        else
            return AjaxResult.SUCCESS(result);
    }

    /**
     * 删除志愿
     */
    @PostMapping("/deleteVolunteer")
    public AjaxResult<Long> deleteVolunteer(@RequestBody DeleteVolunteerParams deleteVolunteerParams) {
        Long result = volunteerService.setVolunteer(UserUtils.getUserId(),
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
     * 如果第二个id是0的话，说明和一个空的志愿进行交换
     *
     */
    @PostMapping("/swapVolunteer")
    public AjaxResult<Long> swapVolunteer(@RequestBody SwapVolunteerParams swapVolunteerParams) {

        if(swapVolunteerParams.getFirstVolunteerPosition() < 1 || swapVolunteerParams.getFirstVolunteerPosition() > 96
                ||swapVolunteerParams.getSecondVolunteerPosition() < 1 || swapVolunteerParams.getSecondVolunteerPosition() > 96) {
            return AjaxResult.FAIL("需要交换志愿的边界出现异常");
        }


        volunteerService.setVolunteer(UserUtils.getUserId(),
                swapVolunteerParams.getFormId(),
                swapVolunteerParams.getSection(),
                swapVolunteerParams.getFirstVolunteerPosition(),
                VolunteerConstant.EMPTY_VOLUNTEER);
        volunteerService.setVolunteer(UserUtils.getUserId(),
                swapVolunteerParams.getFormId(),
                swapVolunteerParams.getSection(),
                swapVolunteerParams.getSecondVolunteerPosition(),
                VolunteerConstant.EMPTY_VOLUNTEER);

        volunteerService.setVolunteer(UserUtils.getUserId(),
                swapVolunteerParams.getFormId(),
                swapVolunteerParams.getSection(),
                swapVolunteerParams.getFirstVolunteerPosition(),
                swapVolunteerParams.getSecondVolunteerId());
        Long result = volunteerService.setVolunteer(UserUtils.getUserId(),
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
     * //TODO 如果上一个已经有了，则变为交换
     */
    @PostMapping("/upVolunteer")
    public AjaxResult<Long> upVolunteer(@RequestBody UpVolunteerParams upVolunteerParams) {

        if(upVolunteerParams.getVolunteerPosition() <= 1 || upVolunteerParams.getVolunteerPosition() > 96) {
            return AjaxResult.FAIL("待上移志愿的边界出现问题");
        }

        FormVolunteer formVolunteer = volunteerService.findByFormIdAndSectionAndVolunteerPosition(upVolunteerParams.getFormId(), upVolunteerParams.getSection(), upVolunteerParams.getVolunteerPosition() - 1);

        volunteerService.setVolunteer(UserUtils.getUserId(),
                upVolunteerParams.getFormId(),
                upVolunteerParams.getSection(),
                upVolunteerParams.getVolunteerPosition(),
                VolunteerConstant.EMPTY_VOLUNTEER);
        Long result = volunteerService.setVolunteer(UserUtils.getUserId(),
                upVolunteerParams.getFormId(),
                upVolunteerParams.getSection(),
                upVolunteerParams.getVolunteerPosition() - 1,
                upVolunteerParams.getVolunteerId());

        if(formVolunteer != null) {
            volunteerService.setVolunteer(UserUtils.getUserId(), formVolunteer.getFormId(), formVolunteer.getVolunteerSection(), formVolunteer.getVolunteerPosition() + 1, formVolunteer.getVolunteerId());
        }


        if (result == -1L)
            return AjaxResult.FAIL("操作失败");
        else
            return AjaxResult.SUCCESS(result);

    }

    /**
     * 下调志愿
     * 本质是和下一个交换位置
     * //TODO 参考上面的todo
     */
    @PostMapping("/downVolunteer")
    public AjaxResult<Long> downVolunteer(@RequestBody DownVolunteerParams downVolunteerParams) {

        if(downVolunteerParams.getVolunteerPosition() >= 96 || downVolunteerParams.getVolunteerPosition() < 1) {
            return AjaxResult.FAIL("待下移志愿的边界出现异常");
        }

        FormVolunteer formVolunteer = volunteerService.findByFormIdAndSectionAndVolunteerPosition(downVolunteerParams.getFormId(), downVolunteerParams.getSection(), downVolunteerParams.getVolunteerPosition() + 1);

        volunteerService.setVolunteer(UserUtils.getUserId(),
                downVolunteerParams.getFormId(),
                downVolunteerParams.getSection(),
                downVolunteerParams.getVolunteerPosition(),
                VolunteerConstant.EMPTY_VOLUNTEER);
        Long result = volunteerService.setVolunteer(UserUtils.getUserId(),
                downVolunteerParams.getFormId(),
                downVolunteerParams.getSection(),
                downVolunteerParams.getVolunteerPosition() + 1,
                downVolunteerParams.getVolunteerId());

        if(formVolunteer != null) {
            volunteerService.setVolunteer(UserUtils.getUserId(), formVolunteer.getFormId(), formVolunteer.getVolunteerSection(), formVolunteer.getVolunteerPosition() - 1, formVolunteer.getVolunteerId());
        }

        if (result == -1L)
            return AjaxResult.FAIL("操作失败");
        else
            return AjaxResult.SUCCESS(result);

    }

}
