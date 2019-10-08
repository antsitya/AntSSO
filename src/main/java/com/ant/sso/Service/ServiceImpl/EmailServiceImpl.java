package com.ant.sso.Service.ServiceImpl;

import com.ant.sso.Entity.Email;
import com.ant.sso.Mapper.EmailMapper;
import com.ant.sso.Repository.EmailRepository;
import com.ant.sso.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private EmailMapper emailMapper;
    @Override
    public List<Email> findNotSend() {
        return emailRepository.findAllNotSend();
    }
}
