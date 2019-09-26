package com.ant.sso.Service.ServiceImpl;

import com.ant.sso.Entity.User;
import com.ant.sso.Mapper.UserMapper;
import com.ant.sso.Repository.UserRepository;
import com.ant.sso.Service.UserService;
import com.ant.sso.Utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonUserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    public User register(String nickName,String password, int type) {
        User user=User.userInit(nickName,password);
        if(type==0){
            userRepository.save(user);
        }else{
            userMapper.insert(user);
        }
        return user;
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public User checkUserLogin(String key, String password) {
        return userRepository.userLogin(key,password);
    }

    @Override
    public User checkLoginOutPwd(String key) {
        return userRepository.loginFind(key);
    }
}
