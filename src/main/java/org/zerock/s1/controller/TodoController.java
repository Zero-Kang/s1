package org.zerock.s1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo/*")
public class TodoController {


    @GetMapping(value ="/test" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> test(){

        return new ResponseEntity<>("TEST", HttpStatus.OK);
    }

}
