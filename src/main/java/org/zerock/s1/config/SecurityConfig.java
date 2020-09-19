package org.zerock.s1.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.zerock.s1.security.filter.ApiCheckFilter;
import org.zerock.s1.security.filter.ApiLoginFilter;
import org.zerock.s1.security.handler.Api401Handler;

import javax.servlet.http.HttpServletRequest;

@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        log.info("configure...............");

        http.authorizeRequests()
                .antMatchers("/sample/exAll").permitAll()
                .antMatchers("/sample/exMember").hasRole("USER")
        ;

        http.formLogin();
        http.oauth2Login();
        http.csrf().disable();


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Bean
    public ApiCheckFilter apiCheckFilter(){

        return new ApiCheckFilter("/api/**/*");

    }

    @Bean
    public ApiLoginFilter apiLoginFilter() throws Exception {
        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/member/apiLogin");

        apiLoginFilter.setAuthenticationFailureHandler(new Api401Handler());

        apiLoginFilter.setAuthenticationManager(authenticationManager());

        return apiLoginFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
