package com.ant.sso.Service;

import com.ant.sso.Entity.User;

import java.util.List;

public interface UserService {
    User register(String nickName,String password,int type);
    List<User> allUsers();
    User checkUserLogin(String key,String password);
    User checkLoginOutPwd(String key);
}
