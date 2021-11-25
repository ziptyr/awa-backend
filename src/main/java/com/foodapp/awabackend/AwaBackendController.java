package com.foodapp.awabackend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AwaBackendController {
    
    @GetMapping
    public ResponseEntity<String> helloWorld() {
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }
}
