package com.ant.sso.Repository;

import com.ant.sso.Entity.SysConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysConfigRepository extends JpaRepository<SysConfig,Integer> {

    @Query("from SysConfig where groups=:groups and state=:state")
    List<SysConfig> getGroupSysConfigs(@Param(value = "groups")String groups,@Param("state")Short state);

    @Query("from SysConfig where sysKey=:sysKey and state=:state")
    SysConfig getSysConfigByKey(@Param("sysKey")String sysKey,@Param("state")Short state);
}
