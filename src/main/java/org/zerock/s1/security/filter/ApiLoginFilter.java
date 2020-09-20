package org.zerock.s1.security.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.zerock.s1.security.service.CustomUserDetailsService;
import org.zerock.s1.security.token.JwtUtils;
import org.zerock.s1.security.user.CustomAuthUser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {

    private JwtUtils jwtUtils;

    public ApiLoginFilter(String defaultFilterProcessesUrl, JwtUtils jwtUtils) {

        super(defaultFilterProcessesUrl);
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        log.info("-----------------ApiLoginFilter---------------------");
        log.info("attemptAuthentication");

        String email = request.getParameter("email");
        String pw = "1111";

        if(email == null){
            throw new BadCredentialsException("email cannot be null");
        }

        UsernamePasswordAuthenticationToken inputToken =
                new UsernamePasswordAuthenticationToken(email, pw);

        return getAuthenticationManager().authenticate(inputToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("-----------------ApiLoginFilter---------------------");
        log.info("successfulAuthentication: " + authResult);

        log.info(authResult.getDetails());
        log.info(authResult.getCredentials());

        log.info(authResult.getPrincipal());

        //email address
        String zerockMemerJSON = ((CustomAuthUser)authResult.getPrincipal()).getUsername();


        String token = jwtUtils.generateJwtToken(zerockMemerJSON);

        response.getOutputStream().write(token.getBytes());

        log.info(token);

    }

}













