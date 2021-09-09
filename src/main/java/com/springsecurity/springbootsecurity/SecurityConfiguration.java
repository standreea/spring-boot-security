package com.springsecurity.springbootsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER=LoggerFactory.getLogger(SecurityConfiguration.class);
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin").password(passwordEncoder().encode("admin111")).authorities("ACCESS_TEST1","ACCESS_TEST2","ROLE_ADMIN")
            .and()
            .withUser("johndoe").password(passwordEncoder().encode("johndoe111")).roles("USER")
            .and()
            .withUser("manager").password(passwordEncoder().encode("manager111")).authorities("ACCESS_TEST1","ROLE_MANAGER");

            LOGGER.info("Configured in-memory users");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {        
        http.authorizeRequests()
            .antMatchers("/index.html").permitAll()
            .antMatchers("/profile/**").authenticated()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/management/**").hasAnyRole("ADMIN","MANAGER")
            //protect REST resources through permissions
            .antMatchers("/api/public/test1").hasAuthority("ACCESS_TEST1")
            .antMatchers("/api/public/test2").hasAuthority("ACCESS_TEST2")
            .and()
            .formLogin()
            .loginPage("/login").permitAll()
            .and()
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
