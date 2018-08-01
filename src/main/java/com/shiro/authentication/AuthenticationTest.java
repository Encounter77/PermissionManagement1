package com.shiro.authentication;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.jupiter.api.Test;

public class AuthenticationTest {

    //用户登录和退出
    @Test
    public void testLoginAndLogout(){

        //1. 通过ini文件构建SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-simple.ini");

        //2. 创建SecurityManager
        SecurityManager securityManager = factory.getInstance();

        //3. 将SecurityManager设置到当前的环境中
        SecurityUtils.setSecurityManager(securityManager);

        //4. 从SecurityManager中创建一个subject
        Subject subject = SecurityUtils.getSubject();

        //5. 在认证提交前准备token -- 令牌
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "1234");

        try {
            //6. 执行认证提交
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

        //7. 是否认证通过
        System.out.println("shiro--登录是否认证通过：" + subject.isAuthenticated());

        //8. 退出操作
        subject.logout();

    }

    //自定义realm认证
    @Test
    public void customRealm(){

        //1. 通过ini文件构建SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");

        //2. 创建SecurityManager
        SecurityManager securityManager = factory.getInstance();

        //3. 将SecurityManager设置到当前的环境中
        SecurityUtils.setSecurityManager(securityManager);

        //4. 从SecurityManager中创建一个subject
        Subject subject = SecurityUtils.getSubject();

        //5. 在认证提交前准备token -- 令牌
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123");

        try {
            //6. 执行认证提交
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

        //7. 是否认证通过
        System.out.println("shiro--登录是否认证通过：" + subject.isAuthenticated());

        //8. 退出操作
        subject.logout();

    }
}
