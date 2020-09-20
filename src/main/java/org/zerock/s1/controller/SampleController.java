package org.zerock.s1.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sample/")
@Log4j2
public class SampleController {

    @GetMapping("/exAll")
    public void exAll() {
        log.info("exAll.................");
    }

    @GetMapping("/exMember")
    public void exMember() {
        log.info("exMember.................");
    }

    @GetMapping("/exAdmin")
    public void exAdmin() {
        log.info("exAdmin.................");
    }


    @GetMapping("/callAjax")
    public void callAjax(){

    }
}
