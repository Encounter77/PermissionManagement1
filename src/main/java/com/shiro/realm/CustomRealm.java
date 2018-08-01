package com.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;

/*
* 自定义realm -- 模拟数据库操作
* */
public class CustomRealm extends AuthorizingRealm {


    //复写命名方法
    @Override
    public void setName(String name) {
        super.setName("CustomRealm");
    }

    //用于认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        // 从token中取出身份信息
        String userCode = (String) authenticationToken.getPrincipal();

        // 根据用户输入的userCode从数据库中查询密码 -- 这里模拟数据库查询结果
        String passWord = "123";

        //查询不到返回Null

        //查询到返回认证信息AuthenticationInfo
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userCode, passWord, this.getName());
        return simpleAuthenticationInfo;
    }

    //用于授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        //从principle中获取主体身份信息
        String userCode  = (String) principalCollection.getPrimaryPrincipal();

        //根据身份信息，获取权限信息 -- 模拟数据库连接
        List<String> permissions = new ArrayList<String>();
        permissions.add("user:create"); //用户创建权限
        permissions.add("items:add");   //商品添加权限

        //查询到权限数据，返回授权信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        //将授权信息添加到SimpleAuthorizationInfo中
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }
}
