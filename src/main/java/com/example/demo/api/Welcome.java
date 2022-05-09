package com.example.demo.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "https://leicon-rw.vercel.app")
@RestController
public class Welcome {
    @GetMapping("/")
    public String welcomeMessage(){
      return "Welcome to leicon api";
    }
}
