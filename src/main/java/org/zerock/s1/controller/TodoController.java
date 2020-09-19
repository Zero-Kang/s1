package org.zerock.s1.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todo/*")
@Log4j2
public class TodoController {

    @GetMapping(value ="/test" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> test(){

        log.info("---------------TodoController test......................");

        return new ResponseEntity<>("test......", HttpStatus.OK);
    }

}
