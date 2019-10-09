package com.ant.sso.Utils;

import com.ant.sso.Common.AntConstant;
import com.ant.sso.Common.AntException;
import com.ant.sso.Common.AntResponseCode;
import com.ant.sso.Config.SysConfigConstant;
import com.ant.sso.Entity.Email;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Log4j2
@Service
public class EmailUtils {

    public void send(Email email){
        email.checkSend();
        try{
            if(StringUtils.isEmpty(email.getAttachments())) {
                sendSimpleEmail(email);
            } else {
                sendAttachmentsEmail(email,false);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("邮件发送异常");
            throw new AntException(AntResponseCode.EMAIL_EXCEPTION);
        }
    }

    private void sendAttachmentsEmail(Email email,boolean isHtml) throws Exception{
        MimeMessage mimeMessage = mailSender.createMimeMessage();//附件处理需要进行二进制传输
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true, AntConstant.DEFAULT_ENCODING);
        helper.setFrom(new InternetAddress(sysConfigConstant.getEmailConfig().getUserName(), "智蚁联创", "UTF-8"));
        helper.setTo(email.getToAddress().split(","));
        helper.setText(email.getContent(),isHtml);
        helper.setSubject(email.getSubject());
        if(!StringUtils.isEmpty(email.getCcTo())) helper.setCc(email.getCcTo().split(","));
        //处理附件
        FileSystemResource resource;
        String fileName;
        for(String attachmentFilePath:email.getAttachments().split(",")){
            resource = new FileSystemResource(new File(attachmentFilePath));
            if(!resource.exists()){
                log.error("{}附件不存在!",attachmentFilePath);
                continue;//开启下一个资源的处理
            }
            fileName = resource.getFilename();
            try{
                helper.addAttachment(fileName,resource);//添加附件
            }catch(MessagingException e){
                e.printStackTrace();
                log.error("邮件{}，附件{}添加失败{}",email.getEmailId(),attachmentFilePath,e.getMessage());
            }
        }
        mailSender.send(mimeMessage);
    }

    private void sendSimpleEmail(Email email)throws Exception{
        String fromByte = new String(("智蚁联创 <" + sysConfigConstant.getEmailConfig().getUserName() + ">").getBytes("UTF-8"));
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();//创建简单邮件对象
        simpleMailMessage.setTo(email.getToAddress().split(","));
        simpleMailMessage.setFrom(fromByte);
        simpleMailMessage.setSubject(email.getSubject());
        simpleMailMessage.setText(email.getContent());
        if(!StringUtils.isEmpty(email.getCcTo())) simpleMailMessage.setCc(email.getCcTo().split(","));
        mailSender.send(simpleMailMessage);
        log.info("邮件发送成功--邮件Id:{}",email.getEmailId());
    }

    @Autowired
    private SysConfigConstant sysConfigConstant;
    @Autowired
    private TemplateEngine templateEngine;//模板引擎解析对象，用于解析模板
    @Autowired
    private JavaMailSender mailSender;//邮件发送的对象，用于邮件发送
}
