package com.ant.sso.Controller;

import com.alibaba.fastjson.JSON;
import com.ant.sso.Common.AntConstant;
import com.ant.sso.Common.AntResponse;
import com.ant.sso.Common.AntResponseCode;
import com.ant.sso.Common.BaseController;
import com.ant.sso.Entity.User;
import com.ant.sso.Service.UserService;
import com.ant.sso.Utils.Md5Utils;
import com.ant.sso.Utils.RedisUtils;
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
    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping(value = "/jwt/login")
    public AntResponse loginJWT(String key,String password){
        AntResponse antResponse=new AntResponse();
        //TODO 参数校验     待优化
        if(StringUtils.isEmpty(key)||StringUtils.isEmpty(password)){
            antResponse.setError(AntResponseCode.ILLEGAL_PARAMETER);
        }else{
            try{
                String passwordMD5= Md5Utils.generateHash(password);
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
                log.error(AntConstant.LOGGER_PREFIX+" loginJWT ",e);
            }
        }
        return antResponse;
    }

    @RequestMapping(value = "/store/login")
    public AntResponse loginStore(String key,String password){
        AntResponse antResponse=new AntResponse();
        if(StringUtils.isEmpty(key)||StringUtils.isEmpty(password)){
            antResponse.setError(AntResponseCode.ILLEGAL_PARAMETER);
            return antResponse;
        }
        try{
            String pwdMD5= Md5Utils.generateHash(password);
            User user=userService.checkLoginOutPwd(key);
            if(user==null){
                antResponse.setError(AntResponseCode.USER_NOT_EXIST);
            }else{
                if(user.getPassword().equals(pwdMD5)){
                    boolean redisRes=redisUtils.getAndSet("USER_"+user.getUserId(), JSON.toJSONString(user));
                    if(redisRes){
                        antResponse.setSuccess(user);
                    }else{
                        antResponse.setError(AntResponseCode.REDIS_EXCEPTION_CODE);
                    }
                }else {
                    antResponse.setError(AntResponseCode.LOGIN_PASSWORD_ERROR);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(AntConstant.LOGGER_PREFIX+" loginStore ",e);
            antResponse.setError(AntResponseCode.EXCEPTION_CODE);
        }
        return antResponse;
    }
}
