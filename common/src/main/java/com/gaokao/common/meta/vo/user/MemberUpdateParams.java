package com.gaokao.common.meta.vo.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MemberUpdateParams extends MemberCreateParams {

    /**
    * 用户名
    */
    @NotBlank(message = "用户名不能为空")
    private String username;
    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能空")
    private String phone;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能空")
    private String password;

    /**
     * 昵称
     */
    private String nickname;

}
