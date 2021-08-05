package com.gaokao.controller;

import com.gaokao.common.meta.AjaxResult;
import com.gaokao.common.meta.vo.user.UserMemberVO;
import com.gaokao.common.service.UserMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author attack204
 * date:  2021/8/5
 * email: 757394026@qq.com
 */
@Slf4j
@RestController
@RequestMapping("/xhr/v1/usermembers")
public class UserMemberManageController {

    @Autowired
    private UserMemberService userMemberService;

    @GetMapping("/")
    public AjaxResult<Page<UserMemberVO>> list(@RequestParam(required = false, defaultValue = "") String keyword,
                                               @RequestParam(required = false, defaultValue = "1") Integer page,
                                               @RequestParam(required = false, defaultValue = "10") Integer size)  {
        return AjaxResult.SUCCESS(userMemberService.list(keyword, page, size));
    }

}
