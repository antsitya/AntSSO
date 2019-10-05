package com.ant.sso.Service.ServiceImpl;

import com.ant.sso.Entity.SysConfig;
import com.ant.sso.Mapper.SysConfigMapper;
import com.ant.sso.Repository.SysConfigRepository;
import com.ant.sso.Service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;
    @Autowired
    private SysConfigRepository sysConfigRepository;

    @Override
    public List<SysConfig> getSysConfigsByGroup(String groups, Short state) {
        return sysConfigRepository.getGroupSysConfigs(groups,state);
    }

    @Override
    public SysConfig getSysConfigByKey(String key, Short state) {
        return sysConfigRepository.getSysConfigByKey(key,state);
    }
}
