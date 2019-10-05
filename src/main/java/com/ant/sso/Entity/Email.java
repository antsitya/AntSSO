package com.ant.sso.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@TableName(value = "Email")
@Table(name = "Email")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    @Column(name = "email_id")
    private Long emailId;
    @Column(name = "to_address")
    private String toAddress;//接受邮者邮件的地址
    private short validate=0;//是否需要身份验证
    private String subject;//邮件主题
    private String content;//邮件文本内容
//    private String[] attachFileName;//附件文件名
    private short state;//邮件状态 0：待发送，1：发送成功 2：发送失败
    private short type;//邮件类别
    private short weight;//邮件优先级
}
