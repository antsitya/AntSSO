package com.ant.sso.Common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ant.sso.Entity.User;
import com.ant.sso.Utils.JSONUtils;
import com.ant.sso.Utils.RedisUtils;
import com.ant.sso.Utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    /**
     *  检查用户是否登录（类似于淘宝天猫，去第三方存储中介获取用户数据信息）
     */
    protected User checkUserAuthLogin(HttpServletRequest request){
        String key=request.getParameter("userKey");
        String userJson=redisUtils.get(key);
        return StringUtils.isEmpty(userJson)?null:JSONObject.toJavaObject(JSON.parseObject(userJson),User.class);
    }

    @Autowired
    private RedisUtils redisUtils;

}
