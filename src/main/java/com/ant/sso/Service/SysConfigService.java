package com.ant.sso.Service;

import com.ant.sso.Entity.SysConfig;

import java.util.List;

public interface SysConfigService {
    List<SysConfig> getSysConfigsByGroup(String groups,Short state);
    SysConfig getSysConfigByKey(String key,Short state);
}
