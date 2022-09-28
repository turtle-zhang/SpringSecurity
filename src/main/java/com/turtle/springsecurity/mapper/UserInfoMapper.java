package com.turtle.springsecurity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.turtle.springsecurity.entity.Menu;
import com.turtle.springsecurity.entity.Role;
import com.turtle.springsecurity.entity.Users;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoMapper  extends BaseMapper<Users> {
    /**
     * 根据用户 Id 查询用户角色
     *
     * @param userId
     * @return
     */
    List<Role> selectRoleByUserId(Long userId);

    /**
     * 根据用户 Id 查询菜单
     *
     * @param userId
     * @return
     */
    List<Menu> selectMenuByUserId(Long userId);
}
