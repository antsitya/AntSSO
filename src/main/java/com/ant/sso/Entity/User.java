package com.ant.sso.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@TableName(value = "User")//mybatis注解
@Table(name = "User")//JPA注解
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_name")
    private String userName;//用户名（唯一）
    private String password;
    @Column(name = "nick_name")
    private String nickName;//昵称
    private Short sex=0;//性别--0：未知     1：男     2：女
    private Date birthday;
    private Short state=0;//状态--0：待激活     1：正常    2：禁用
    private Short type=0;//类别--0：蚂圈基础用户
    @Column(name = "register_time")
    private Date registerTime;
    @Column(name = "real_name")
    private String realName;//真实姓名
    private String email;
    private Integer cellphone;

    public static User userInit(String nickName,String password){
        User user=new User();
        user.setNickName(nickName);
        user.setPassword(password);
        user.setRegisterTime(new Date());
        return user;
    }
}
