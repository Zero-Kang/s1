package org.zerock.s1.security.filter;

import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zerock.s1.security.token.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
public class ApiCheckFilter extends OncePerRequestFilter {

    private String pattern;
    private AntPathMatcher antPathMatcher;
    private JwtUtils jwtUtils;

    public ApiCheckFilter(String pattern, JwtUtils jwtUtils) {
        this.pattern = pattern;
        this.antPathMatcher = new AntPathMatcher();
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("---------ApiCheckFilter doFilterInternal--------------");

        log.info("REQUESTURI: " + request.getRequestURI());

        log.info(antPathMatcher.match(pattern, request.getRequestURI()));

        //필터가 동작해야하는 경우
        if(antPathMatcher.match(pattern, request.getRequestURI())){

            String jwt = parseJwt(request);

            log.info("jwt: " + jwt);
            log.info("jwt validate result: " + jwtUtils.validateJwtToken(jwt));

            //check condition
            if(jwt == null || jwtUtils.validateJwtToken(jwt) == false){

                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                // json 리턴 및 한글깨짐 수정.
                response.setContentType("application/json;charset=utf-8");
                JSONObject json = new JSONObject();
                String message = "FAIL CHECK API TOKEN";
                json.put("code", "403");
                json.put("message", message);

                PrintWriter out = response.getWriter();
                out.print(json);

                return;
            }



            filterChain.doFilter(request, response);
            return;
        }

        //필터가 동작하지 않아도 되는 경우
        filterChain.doFilter(request, response);

    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}
