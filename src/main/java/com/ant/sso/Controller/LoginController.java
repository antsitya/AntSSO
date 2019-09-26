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

import java.util.List;

@Log4j2
@RestController
@RequestMapping(value = "/api/system")
public class LoginController extends BaseController {

    @RequestMapping(value = "/register/common")
    public String commonRegister(String nickName,String password,Integer type){
        if(StringUtils.isEmpty(nickName)||StringUtils.isEmpty(password)){
            log.error(AntSysConstant.LOGGER_PREFIX+"commonRegister");
            return "empty error";
        }
        try{
            String passwordHashValue= Md5Util.generateHash(password);
            User user=userService.register(nickName,passwordHashValue,type);
            return "OK:"+user.toString();
        }catch (Exception e){
            e.printStackTrace();
            log.error("error happen on function commonRegister ",e);
            return "exception happen "+e;
        }
    }

    @RequestMapping(value = "/searchAll")
    public AntResponse searchAll(){
        AntResponse antResponse=new AntResponse();
        try{
            List<User> users=userService.allUsers();
            antResponse.setSuccess(users);
        }catch (Exception e){
            e.printStackTrace();
            log.error(AntSysConstant.LOGGER_PREFIX+" searchAll ",e);
            antResponse.setError(AntResponseCode.EXCEPTION_CODE);
        }
        return antResponse;
    }

    @Autowired
    private UserService userService;

}
