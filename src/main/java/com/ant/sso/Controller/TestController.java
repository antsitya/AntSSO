package com.ant.sso.Controller;

import com.ant.sso.Common.AntResponse;
import com.ant.sso.Common.AntResponseCode;
import com.ant.sso.Common.BaseController;
import com.ant.sso.Entity.User;
import com.ant.sso.Utils.RedisUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/test")
public class TestController extends BaseController {
    @Resource
    private RedisUtils redisUtils;

    @RequestMapping(value = "/setRedis")
    public AntResponse setRedis(String key, String value){
        AntResponse antResponse=new AntResponse();
        boolean res=redisUtils.set(key,value);
        antResponse.setSuccess(res);
        return antResponse;
    }

    @RequestMapping(value = "/getRedis")
    public AntResponse getRedis(String key){
        AntResponse antResponse=new AntResponse();
        String res=redisUtils.get(key);
        antResponse.setSuccess(res);
        return antResponse;
    }

    @RequestMapping(value = "/getRedisUser")
    public AntResponse getRedisUser(HttpServletRequest request){
        AntResponse antResponse=new AntResponse();
        User user=checkUserAuthLogin(request);
        //TODO 优化 checkUserAuthLogin方法中一旦用户未登录抛出异常
        if(user==null){
            antResponse.setError(AntResponseCode.USER_NOT_EXIST);
        }else{
            antResponse.setSuccess(user);
        }
        return antResponse;
    }
}
