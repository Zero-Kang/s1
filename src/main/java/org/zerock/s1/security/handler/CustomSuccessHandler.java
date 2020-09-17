package org.zerock.s1.security.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.zerock.s1.security.user.CustomAuthUser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Log4j2
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

        log.info("----------------------------------------------------");
        log.info("onAuthenticationSuccess filterchain");

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        log.info("------------------------OAuth2AuthenticationToken----------------------------");

        log.info(authentication);

        CustomAuthUser customAuthUser = (CustomAuthUser)authentication.getPrincipal();

        log.info("Need Modify Member?" + customAuthUser.isSocial());

        if(customAuthUser.isSocial()){

            redirectStratgy.sendRedirect(request, response, "/member/modify?from=social");
            return;
        }


        //redirect.....................................
        RequestCache requestCache = new HttpSessionRequestCache();

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String targetUrl = savedRequest.getRedirectUrl();

        log.info("targetURL : " + targetUrl);

        redirectStratgy.sendRedirect(request, response, targetUrl);


    }
}
