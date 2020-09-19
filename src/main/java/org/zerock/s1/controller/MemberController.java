package org.zerock.s1.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/member/")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/modify")
    public void modifyGET(){

        log.info("modify...get...........................");


    }


    @RequestMapping(value = "/apiLogin", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String apiLogin(HttpServletResponse response){




        return "published....";
    }

}
