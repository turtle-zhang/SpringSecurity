package com.turtle.springsecurity.entity;

import lombok.Data;

/**
 * ่ๅ่กจ
 */
@Data
public class Menu {
    private Long id;
    private String name;
    private String url;
    private Long parentId;
    private String permission;

}
