package com.ant.sso;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.ant.sso.Mapper")
@EnableAspectJAutoProxy
public class SsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoApplication.class, args);
        System.out.println("#######################################^v^#######################################");
        System.out.println("###################################项目启动成功###################################");
    }

}
