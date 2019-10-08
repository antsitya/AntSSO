package com.ant.sso.Utils;

import com.ant.sso.Config.SysConfigConstant;
import com.ant.sso.Entity.Email;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

@Log4j2
@Service
public class EmailUtils {

    public void send(Email email){
        email.checkSend();
        if(StringUtils.isEmpty(email.getAttachments())) sendSimpleEmail(email);
        else sendAttachmentsEmail(email);
    }

    private void sendAttachmentsEmail(Email email){

    }

    private void sendSimpleEmail(Email email){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();//创建简单邮件对象
        simpleMailMessage.setTo(email.getToAddress().split(","));
        simpleMailMessage.setFrom(sysConfigConstant.getEmailConfig().getUserName());
        simpleMailMessage.setSubject(email.getSubject());
        simpleMailMessage.setText(email.getContent());
        if(!StringUtils.isEmpty(email.getCcTo())) simpleMailMessage.setCc(email.getCcTo().split(","));
        mailSender.send(simpleMailMessage);
        log.info("邮件发送成功--邮件Id:",email.getEmailId());
    }

    @Autowired
    private SysConfigConstant sysConfigConstant;
    @Autowired
    private TemplateEngine templateEngine;//模板引擎解析对象，用于解析模板
    @Autowired
    private JavaMailSender mailSender;//邮件发送的对象，用于邮件发送
}
