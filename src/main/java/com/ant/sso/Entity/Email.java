package com.ant.sso.Entity;

import com.ant.sso.Common.AntException;
import com.ant.sso.Common.AntResponseCode;
import com.ant.sso.Utils.StringUtils;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;

@Log4j2
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
    private String toAddress;//接收邮者邮件的地址
    private short validate=0;//是否需要身份验证
    private String subject;//邮件主题
    private String content;//邮件文本内容
//    private String[] attachFileName;//附件文件名
    private short state;//邮件状态 0：待发送，1：发送成功 2：发送失败  3:发送中
    private short type;//邮件类别
    private short weight;//邮件优先级
    @Column(name = "cc_to")
    private String ccTo;//抄送人，","分隔
    private String attachments;//附件（路径，","分隔）

    public void checkSend(){
        if(StringUtils.isEmpty(this.subject)||StringUtils.isEmpty(this.toAddress)||StringUtils.isEmpty(this.content)){
            log.error("邮件信息不完整");
            throw new AntException(AntResponseCode.EMAIL_EMPTY_EXCEPTION);
        }
        if(this.state==1){
            log.error("邮件已发送，不可重复发送");
            throw  new AntException(AntResponseCode.EMAIL_HAS_SEND);
        }
    }
}
