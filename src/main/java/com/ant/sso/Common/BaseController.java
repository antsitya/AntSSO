package com.ant.sso.Common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ant.sso.Entity.User;
import com.ant.sso.Utils.RedisUtils;
import com.ant.sso.Utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    /**
     *  检查用户是否登录（类似于淘宝天猫，去第三方存储中介获取用户数据信息）
     *  未登录返回null,登录返回user对象
     */
    protected User checkUserLogin(HttpServletRequest request){
        String key=request.getParameter("userKey");
        String userJson=redisUtils.get(key);
        return StringUtils.isEmpty(userJson)?null:JSONObject.toJavaObject(JSON.parseObject(userJson),User.class);
    }

    /**
     *  检查用户登录状态，未登录返回异常
     */
    //TODO 待完善自定义异常（登录异常，参数异常）
    protected User requireLogin(HttpServletRequest request){
        String key=request.getParameter("userKey");
        if(StringUtils.isEmpty(key)) throw new AntException(AntResponseCode.ILLEGAL_PARAMETER);
        String userJson=redisUtils.get(key);
        if(StringUtils.isEmpty(userJson)) throw new AntException(AntResponseCode.USER_NOT_LOGIN);
        return JSONObject.toJavaObject(JSON.parseObject(userJson),User.class);
    }

    @Autowired
    private RedisUtils redisUtils;

}
