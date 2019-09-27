package com.ant.sso.Controller;

import com.ant.sso.Common.AntResponse;
import com.ant.sso.Common.AntResponseCode;
import com.ant.sso.Common.BaseController;
import com.ant.sso.Entity.User;
import com.ant.sso.Service.UserService;
import com.ant.sso.Utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/test")
public class TestController extends BaseController {
    @Resource
    private RedisUtils redisUtils;
    @Autowired
    private UserService userService;

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
//        User user=checkUserLogin(request);
        User user=requireLogin(request);
        antResponse.setSuccess(user);
        return antResponse;
    }
    @RequestMapping(value = "/notNull")
    public AntResponse notNull(String userId){
        AntResponse antResponse=new AntResponse();
        User user=userService.findById(Long.valueOf(userId));
        antResponse.setSuccess(user);
        return antResponse;
    }
}
