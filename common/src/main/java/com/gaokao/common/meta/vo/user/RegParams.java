package com.gaokao.common.meta.vo.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author attack204
 * date:  2021/7/19
 * email: 757394026@qq.com
 */
@Data
public class RegParams {
    @NotNull(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机号不合法")
    private String phone;
    @NotNull(message = "密码不能为空")
    @Length(min = 8, max = 32, message = "密码长度不合法")
    private String password;
    @NotBlank(message = "验证码不合法")
    private String veryCode;
}
