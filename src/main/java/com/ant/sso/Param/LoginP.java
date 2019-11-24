package com.ant.sso.Param;

import javax.validation.constraints.NotBlank;

public class LoginP {
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    private String password;
}
