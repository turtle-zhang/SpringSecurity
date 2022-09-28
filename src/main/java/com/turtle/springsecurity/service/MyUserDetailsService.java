package com.turtle.springsecurity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turtle.springsecurity.entity.Menu;
import com.turtle.springsecurity.entity.Role;
import com.turtle.springsecurity.entity.Users;
import com.turtle.springsecurity.mapper.UserInfoMapper;
import com.turtle.springsecurity.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义从数据库查找出对应的账户信息
 */
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        QueryWrapper<Users> wrapper = new QueryWrapper();
        wrapper.eq("username", s);
        Users users = usersMapper.selectOne(wrapper);
        if (users == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        // 控制台数据信息
        System.out.println(users);
        // 赋予默认角色权限，只有具有改权限才能访问  .antMatchers的路径
        // 应该交于数据库维护
        // 添加权限：权限  角色
        /**
         * hasAuthority:权限（单个）
         * hasAnyAuthority:权限（多个之一）
         *
         * hasRole:角色（单个）
         * hasAnyRole:角色（多个之一）
         */

        // hasAuthority:权限（单个）,对应的配置
        // List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
        // .antMatchers("/findAll").hasAuthority("admin") //指定 URL需要有role权限才能访问

        // hasAnyAuthority:权限（多个之一）
        // List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
        // .antMatchers("/findAll").hasAnyAuthority("admin,ADMINSSS") //指定 URL需要有role权限才能访问

        // hasRole:角色（单个） 前缀：ROLE_
        // List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin");
        // .antMatchers("/findAll").hasRole("admin") //指定 URL需要有role权限才能访问

        // hasAnyRole:角色（多个） 前缀：ROLE_
        // List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin,adminss");
        // .antMatchers("/findAll").hasAnyRole("admin,xxx") //指定 URL需要有role权限才能访问


        List<Role> roleList = userInfoMapper.selectRoleByUserId(users.getId());
        List<Menu> menuList = userInfoMapper.selectMenuByUserId(users.getId());

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        // 处理角色
        for (Role role : roleList) {
            grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
        }

        // 处理权限
        for (Menu menu : menuList) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(menu.getPermission()));
        }

        // 返回用户名 密码 角色
        return new User(users.getUsername(), users.getPassword(), grantedAuthorityList);
    }
}
