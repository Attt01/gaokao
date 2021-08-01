package com.gaokao.adminapi.controller.user;

import com.gaokao.common.meta.AjaxResult;
import com.gaokao.common.meta.vo.user.RegParams;
import com.gaokao.common.meta.vo.user.UserUpdateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author attack204
 * date:  2021/7/20
 * email: 757394026@qq.com
 */
@Slf4j
@RestController
@RequestMapping("/xhr/v1/userMember")
public class UserController {

    @GetMapping("/needLogin")
    public AjaxResult<String> needLogin() {
        return null;
    }

    @GetMapping("/sendVerifyCode")
    public AjaxResult<String> sendVerifyCode(String phone) {
        return null;
    }

    /**
     * 更新用户信息
     */
    @PostMapping("/{id}")
    public AjaxResult<Long> update(Authentication authentication, @PathVariable Long id, @Valid @RequestBody UserUpdateParams params) {
        return null;
    }

    @PostMapping("/reg")
    public AjaxResult<Long> reg(@Valid @RequestBody RegParams regParams) {
        return null;
    }


}
