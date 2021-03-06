package com.algaworks.wine.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("mateus").password("123456789").roles("CREATE_WINE", "LIST_WINE").and()
                .withUser("maria").password("123456789").roles("LIST_WINE");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/layout/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/wines/").hasRole("LIST_WINE")
                .antMatchers("/wines/new").hasRole("CREATE_WINE")
                .anyRequest().authenticated()
                .and()
        .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
        .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }
}
