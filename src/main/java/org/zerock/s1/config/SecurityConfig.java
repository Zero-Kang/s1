package org.zerock.s1.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zerock.s1.security.filter.CustomJwtAuthenticationFilter;
import org.zerock.s1.security.service.CustomOAuth2UserService;
import org.zerock.s1.security.service.CustomUserDetailsService;
import org.zerock.s1.security.handler.CustomSuccessHandler;

@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        log.info("configure...............");

        http.authorizeRequests()
                .antMatchers("/sample/exAll").permitAll()
                .antMatchers("/sample/exMember")
                .hasRole("USER");
        http.formLogin()
        .successHandler(customSuccessHandler());
        ;
        http.userDetailsService(userDetailsService);

        http.oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
        .and().successHandler(customSuccessHandler())
        ;

        http.csrf().disable();


        http.antMatcher("/todo/**")
                .addFilterBefore(customJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/todo/test")
                .permitAll()
        ;

    }


    @Bean
    public CustomJwtAuthenticationFilter customJwtAuthenticationFilter() {
        return new CustomJwtAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomSuccessHandler customSuccessHandler() {
        return new CustomSuccessHandler();
    }


}
