package com.ant.sso.Entity;

import com.ant.sso.Common.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "SysConfig")
@TableName(value = "SysConfig")
public class SysConfig extends BaseEntity<SysConfig> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    @Column(name = "sys_id")
    private Integer sysId;
    @Column(name = "sys_key")
    private String sysKey;
    @Column(name = "sys_value")
    private String sysValue;
    private Short state;//配置状态
    private Integer type;
    private String groups;
    private String extend;
}
