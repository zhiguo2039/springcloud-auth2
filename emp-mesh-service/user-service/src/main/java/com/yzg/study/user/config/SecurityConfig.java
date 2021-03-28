package com.yzg.study.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //过滤 高富帅url，不需要验证
        http.requestMatchers()
                .anyRequest()
                .and()
                .authorizeRequests()
                .antMatchers("/api/**","/druid/*","/**/api/**","/actuator/**","/v2/api-docs","/swagger-ui.html","/webjars/**","/swagger-resources/**")
                .permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated();
    }

    /**
     * 例外的url，2中方式均可
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.GET,
                "/favicon.ico",
                "/**/*.png",
                "/**/*.ttf",
                "/*.html",
                "/**/css/bootstrap.min.css",
                "/**/js/jquery.min.js",
                "/*/css/bootstrap.min.css",
                "/*/js/jquery.min.js",
                "/**/*.js",
                "/**/*.css",
                "/druid/**",
                "/**/*.js");
        web.ignoring().antMatchers("/druid/**");


    }

}
