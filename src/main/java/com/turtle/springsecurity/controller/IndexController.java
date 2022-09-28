package com.turtle.springsecurity.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
// @RequestMapping("")
public class IndexController {

    /**
     * 跳转到login.html页面
     *
     * @return
     */
    @GetMapping("/login")
    public String login() {
        System.out.println("进入登录页面");
        return "login";
    }

    /**
     * 跳转到success.html页面
     *
     * @return
     */
    @GetMapping("/index")
    public String index() {
        return "success";
    }

    /**
     * 返回字符串：findAll
     *
     * @return
     */
    @GetMapping("/findAll")
    @ResponseBody
    public String findAll() {
        return "findAll";
    }

    /**
     * 页面提交方式必须为 post 请求，所以上面的页面不能使用，用户名，密码必须为
     * username,password
     *
     * @return
     */
    @PostMapping("/success")
    public String success() {
        return "success";
    }


    @GetMapping("/fail")
    public String fail2() {
        return "fail";
    }


    /**
     * 测试 @Secured 认证授权注解
     *
     * @return
     */
    @GetMapping("/secured")
    @ResponseBody
    @Secured({"ROLE_admin", "ROLE_xxx"}) // 访问的账户需要有其中一个权限
    public String secured() {
        return "@Secured注解使用成功！！！";
    }

    /**
     * 测试 @PreAuthorize 认证授权注解
     * 在方法执行之前校验
     *
     * @return
     */
    @GetMapping("/preAuthorize")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('adminss')")
    public String preAuthorize() {
        return "@PreAuthorize注解使用成功！！！";
    }

    /**
     * 测试 @PostAuthorize 认证授权注解
     * 在方法执行之后校验
     *
     * @return
     */
    @GetMapping("/postAuthorize")
    @ResponseBody
    @PostAuthorize("hasAnyAuthority('adminss')")
    public String postAuthorize() {
        return "@PostAuthorize！！！";
    }

    /**
     * @Secured:角色认证，会加上ROLE_
     *
     * @PostAuthorize:权限认证
     */

    @GetMapping("/Test_01")
    @ResponseBody
    @Secured({"ROLE_管理员"}) // 访问的账户需要有其中一个权限
    public String Test_01() {
        return "测试成功";
    }


    @GetMapping("/Test_02")
    @ResponseBody
    @PostAuthorize("hasAnyAuthority('menu:user')")
    public String Test_02() {
        return "测试成功";
    }
}
