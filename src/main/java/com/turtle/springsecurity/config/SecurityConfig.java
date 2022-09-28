package com.turtle.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * 初始demo的配置类
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PersistentTokenRepository tokenRepository;

    // 注入 PasswordEncoder 类到 spring 容器中
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 方式二：使用配置类。
    /**
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode("123");
        auth.inMemoryAuthentication().withUser("李si").password(password).roles("admin");
    }
    */


    //方式三：使用自定义实现类设置(可加可不加，但是建议加上，思路会更清晰)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    /**
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin() // 表单登录
                .and()
                .authorizeRequests() // 认证配置
                .anyRequest() // 任何请求
                .authenticated(); // 都需要身份验证
    }
    */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 配置认证
        http.formLogin()
                .loginPage("/login.html") // 配置哪个 url 为登录页面
                .loginProcessingUrl("/user/login")   //登录访问路径，这个一定要和登录页面中的配置保持一致，不然会一直循环重定向（403）登录页面
                .defaultSuccessUrl("/index").permitAll()   //登录成功之后，跳转路径
                .failureUrl("/fail.html");   // 登录失败跳转的路径(只能直接跳转html)
                //.usernameParameter("账户名")   如果不使用默认的username  password就可以通过这种方式指定获取
                //.passwordParameter("密码")

        http.authorizeRequests()
                .antMatchers( "/","/user/login").permitAll() // 指定 URL 无需保护。 //表示配置请求路径
                .antMatchers("/findAll").hasAnyRole("admin") //指定 URL需要有role权限才能访问（也可以使用注解实现）
                .anyRequest() // 其他请求
                .authenticated(); //需要认证

        // 关闭 csrf
        http.csrf().disable();

        // 配置没有权限 跳转的自定义页面
        http.exceptionHandling().accessDeniedPage("/unauth.html");

        // 当点击登出操作
        http.logout().logoutUrl("/logout")
                .logoutSuccessUrl("/login").permitAll();  // .permitAll()不要落下了

        // 开启记住我功能
        http.rememberMe()
                .tokenRepository(tokenRepository)
                .userDetailsService(userDetailsService)
                .tokenValiditySeconds(60);// 设置有效时长 60s


    }
}
