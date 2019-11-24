package com.ant.sso.Service;

import com.ant.sso.Entity.Email;

import java.util.List;

public interface EmailService {
    public List<Email> findNotSend();
}
