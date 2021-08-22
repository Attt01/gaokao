package com.gaokao.controller.user;

import com.gaokao.common.dao.UserMemberDao;
import com.gaokao.common.enums.VeryCodeType;
import com.gaokao.common.meta.AjaxResult;
import com.gaokao.common.meta.bo.JwtUser;
import com.gaokao.common.meta.po.UserMember;
import com.gaokao.common.meta.vo.user.MemberUpdateParams;
import com.gaokao.common.meta.vo.user.RegParams;
import com.gaokao.common.meta.vo.user.UserMemberVO;
import com.gaokao.common.meta.vo.user.UserUpdateParams;
import com.gaokao.common.service.UserMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author attack204
 * date:  2021/7/20
 * email: 757394026@qq.com
 */
@ResponseBody
@Slf4j
@RestController
@RequestMapping("/xhr/v1/userMember")
public class UserMemberController {
    @Autowired
    private UserMemberService userMemberService;



    @GetMapping("/needLogin")
    public AjaxResult<String> needLogin() {
        return AjaxResult.UNAUTHORIZED("请登陆或者注册");
    }

    @GetMapping("/sendVerifyCode")
    public AjaxResult<String> sendVerifyCode(String phone) {
        return AjaxResult.SUCCESSMSG(userMemberService.sendVerifyCode(VeryCodeType.REG, phone));
    }

    /**
     * 更新用户信息 通过Authentication.getPrincipal()可以获取到代表当前用户的信息
     */
    @PostMapping("/update/{id}")
    public AjaxResult<Long> update(@PathVariable Long id,
                                   @Valid @RequestBody MemberUpdateParams params) {
            return AjaxResult.SUCCESS(userMemberService.update(id, params));

    }

    @PostMapping("/reg")
    public AjaxResult<Long> reg(@Valid @RequestBody RegParams regParams) {
        return AjaxResult.SUCCESS(userMemberService.reg(regParams));
    }

    @GetMapping("/info")
    public AjaxResult<UserMemberVO> getInfo(Authentication authentication) {
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        return AjaxResult.SUCCESS(userMemberService.getInfo(jwtUser.getId()));
    }


}
