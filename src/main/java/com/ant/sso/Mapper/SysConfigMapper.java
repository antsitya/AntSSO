package com.ant.sso.Mapper;

import com.ant.sso.Entity.SysConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysConfigMapper extends BaseMapper<SysConfig> {

}
