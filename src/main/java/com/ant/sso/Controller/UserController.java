package com.ant.sso.Controller;

import com.ant.sso.Common.AntResponse;
import com.ant.sso.Common.AntResponseCode;
import com.ant.sso.Common.AntSysConstant;
import com.ant.sso.Common.BaseController;
import com.ant.sso.Entity.User;
import com.ant.sso.Service.UserService;
import com.ant.sso.Utils.Md5Util;
import com.ant.sso.Utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping(value = "/api/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/jwt/login")
    public AntResponse loginJWT(String key,String password){
        AntResponse antResponse=new AntResponse();
        //TODO 参数校验     待优化
        if(StringUtils.isEmpty(key)||StringUtils.isEmpty(password)){
            antResponse.setError(AntResponseCode.ILLEGAL_PARAMETER);
        }else{
            try{
                String passwordMD5= Md5Util.generateHash(password);
                User user=userService.checkLoginOutPwd(key);
                if(user==null){
                    antResponse.setError(AntResponseCode.USER_NOT_EXIST);
                }else{
                    if(user.getPassword().equals(passwordMD5)){
                        antResponse.setSuccess(user);
                    }else{
                        antResponse.setError(AntResponseCode.LOGIN_PASSWORD_ERROR);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                antResponse.setError(AntResponseCode.EXCEPTION_CODE);
                log.error(AntSysConstant.LOGGER_PREFIX+" loginJWT ",e);
            }
        }
        return antResponse;
    }
}
