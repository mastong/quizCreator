package com.mastong.quiz.creator.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.mastong.quiz.creator.service.UserServiceImpl;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceImpl userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/newuser").permitAll()
                .antMatchers("/createuser").permitAll()
                .anyRequest()
                    .fullyAuthenticated()
                    .and()
                    .formLogin()
                        .usernameParameter("login")
                        .passwordParameter("password")
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .permitAll()
                    .and()
                    .logout().permitAll();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(new Md5PasswordEncoder());
        authProvider.setUserDetailsService(userService);
        // ReflectionSaltSource saltSource = new ReflectionSaltSource();
        // saltSource.setUserPropertyToUse("salt");
        // authProvider.setSaltSource(saltSource);
        auth.authenticationProvider(authProvider);
        //auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN", "USER").and().withUser("user").password("user").roles("USER");
    }

}
