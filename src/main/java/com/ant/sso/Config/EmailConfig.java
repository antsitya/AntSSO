package com.ant.sso.Config;

import com.ant.sso.Entity.SysConfig;
import lombok.Data;
import java.util.List;

@Data
public class EmailConfig {
    private String host;
    private Integer port;
    private String userName;
    private String password;
    private String timeout;
    private String nickName;

    public static EmailConfig init(List<SysConfig> emailConfigs){
        EmailConfig emailConfig=new EmailConfig();
        for(SysConfig sysConfig:emailConfigs){
            if(sysConfig.getSysKey().equals("Mail_HOST")) emailConfig.setHost(sysConfig.getSysValue());
            if(sysConfig.getSysKey().equals("MAIL_PORT")) emailConfig.setPort(Integer.valueOf(sysConfig.getSysValue()));
            if(sysConfig.getSysKey().equals("MAIL_USERNAME")) emailConfig.setUserName(sysConfig.getSysValue());
            if(sysConfig.getSysKey().equals("MAIL_PASSWORD")) emailConfig.setPassword(sysConfig.getSysValue());
            if(sysConfig.getSysKey().equals("MAIL_TIMEOUT")) emailConfig.setTimeout(sysConfig.getSysValue());
            if(sysConfig.getSysKey().equals("MAIL_NICKNAME")) emailConfig.setNickName(sysConfig.getSysValue());
        }
        return emailConfig;
    }
}
