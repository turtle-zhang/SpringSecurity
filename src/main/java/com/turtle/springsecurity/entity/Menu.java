package com.turtle.springsecurity.entity;

import lombok.Data;

/**
 * 菜单表
 */
@Data
public class Menu {
    private Long id;
    private String name;
    private String url;
    private Long parentId;
    private String permission;

}
