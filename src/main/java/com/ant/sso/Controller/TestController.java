package com.ant.sso.Controller;

import com.ant.sso.Annotation.Login;
import com.ant.sso.Common.AntResponse;
import com.ant.sso.Common.AntResponseCode;
import com.ant.sso.Common.BaseController;
import com.ant.sso.Common.annotation.Check;
import com.ant.sso.Config.EmailConfig;
import com.ant.sso.Config.SysConfigConstant;
import com.ant.sso.Entity.Email;
import com.ant.sso.Entity.SysConfig;
import com.ant.sso.Entity.User;
import com.ant.sso.Service.EmailService;
import com.ant.sso.Param.LoginP;
import com.ant.sso.Service.SysConfigService;
import com.ant.sso.Service.UserService;
import com.ant.sso.Utils.EmailUtils;
import com.ant.sso.Utils.RedisUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(value = "/api/test")
public class TestController extends BaseController {
    @Resource
    private RedisUtils redisUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private SysConfigService sysConfigService;

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

    @RequestMapping(value = "/loginValid")
    public AntResponse loginValid(@Validated LoginP loginDTO){
        AntResponse antResponse=new AntResponse();
//        if(bindingResult.hasErrors()) antResponse.setError(AntResponseCode.ILLEGAL_PARAMETER);

        return antResponse;
    }

    @RequestMapping(value = "/sysConfig")
    public AntResponse sysConfig(){
        AntResponse antResponse=new AntResponse();
        EmailConfig emailConfig=sysConfigConstant.getEmailConfig();
        antResponse.setSuccess(emailConfig);
        return antResponse;
    }
    @RequestMapping(value = "/test1")
    public AntResponse test1(){
        AntResponse antResponse=new AntResponse();
        List<SysConfig> res=sysConfigService.getSysConfigsByGroup("email",(short)1);
        antResponse.setSuccess(res);
        return antResponse;
    }

    @RequestMapping(value = "/test")
    @Check(value = {"param1 not null :param1不可以为空","tel not null :请填写手机号！"})
    public AntResponse test(String param1,String tel){
        AntResponse antResponse=new AntResponse();
        antResponse.setSuccess("hello world!");
        return antResponse;
    }
    @RequestMapping(value = "/test2")
    public AntResponse test2(){
        AntResponse antResponse=new AntResponse();
        antResponse.setSuccess("just do it!");
        return antResponse;
    }
    @RequestMapping(value = "/testEmail")
    public AntResponse testEmail(){
        AntResponse antResponse=new AntResponse();
        try{
            List<Email> emails=emailService.findNotSend();
            for(Email email:emails){
                emailUtils.send(email);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("error happen on function testEmail ",e);
            antResponse.setError(AntResponseCode.EXCEPTION_CODE);
        }
        return antResponse;
    }

    @Login
    @RequestMapping(value = "/testAop")
    public AntResponse testAop(){
        AntResponse antResponse=new AntResponse();
        antResponse.setSuccess("hello world!");
        return antResponse;
    }

    @RequestMapping(value = "/testAop1")
    public AntResponse testAop1(){
        AntResponse antResponse=new AntResponse();
        antResponse.setSuccess("hello world!");
        return antResponse;
    }

    @Autowired
    private SysConfigConstant sysConfigConstant;
    @Autowired
    private EmailService emailService;
    @Autowired
    private EmailUtils emailUtils;
}
