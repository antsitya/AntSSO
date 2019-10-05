package com.ant.sso.Config;

import com.ant.sso.Entity.SysConfig;
import com.ant.sso.Service.SysConfigService;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

@Log4j2
@Data
@Component
public class SysConfigConstant implements Serializable {
    private EmailConfig emailConfig;

    @Autowired
    private SysConfigService sysConfigService;

    @PostConstruct
    public void init() {
        List<SysConfig> emailConfigs = sysConfigService.getSysConfigsByGroup("email", (short) 1);
        this.emailConfig = EmailConfig.init(emailConfigs);
    }
}
