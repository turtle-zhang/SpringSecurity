package com.turtle.springsecurity.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// @Service  不能出现多个注入Spring仓库的UserDetailsService实现类，认证的时候会报错
public class LoginService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 判断用户名是否存在
        if (!"张san".equals(username)) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        // 从数据库中获取的密码 123 的密文
        String pwd = "$2a$10$sesB9zxrxCvotv3NQVz6geMRiuq0jVJMFI0qZaPg9qOd8iFh2IvuW";
        // 第三个参数表示权限
        return new User(username, pwd, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,"));
    }
}
