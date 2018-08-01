package com.shiro.authorization;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class AuthorizationTest {


    //角色授权，资源授权测试
    @Test
    public void testAuthorization(){

        //1. 通过ini文件构建SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-permission.ini");

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

        //基于角色的授权
        //hasRole传入单个角色标识
        boolean isHasRole = subject.hasRole("role1");
        //hasRole传入多个角色标识
        boolean isHasRoles = subject.hasAllRoles(Arrays.asList("role1", "role2"));
        System.out.println("单个角色判断：" + isHasRole + "\n" + "多个角色判断：" + isHasRoles);

        //使用check方法进行授权，授权不通过会抛出异常
//        subject.checkRole("role3");

        //基于资源的授权
        //isPermitted传入单个权限标识符
        boolean isPermitted = subject.isPermitted("user:create:1");
        //isPermitted传入多个权限标识符
        boolean isPermittedAll = subject.isPermittedAll("user:create", "user:delete1");
        System.out.println("单个权限判断：" + isPermitted + "\n" + "多个权限判断：" + isPermittedAll);

        //使用check方法进行授权，授权不通过会抛出异常
//        subject.checkPermission("items:add");
    }

    //自定义realm授权
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

        //基于资源的授权
        //isPermitted传入单个权限标识符
        boolean isPermitted = subject.isPermitted("user:create:1");
        //isPermitted传入多个权限标识符
        boolean isPermittedAll = subject.isPermittedAll("user:create", "user:query");
        System.out.println("单个权限判断：" + isPermitted + "\n" + "多个权限判断：" + isPermittedAll);

        //使用check方法进行授权，授权不通过会抛出异常
//        subject.checkPermission("items:add");
    }

}
