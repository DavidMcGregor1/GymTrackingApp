package com.example.GymTrackingApp;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.*;

@Controller
public class MainController {

    @GetMapping("/hello")
    public String sayHello() {
        System.out.println("arrived at hello page");
        return "index";
    }

}
