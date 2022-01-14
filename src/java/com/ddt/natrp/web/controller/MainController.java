package com.ddt.natrp.web.controller;

import com.example.demo.core.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController extends BaseController {

    @GetMapping("/main")
    public String openMain(){
        return "main";
    }
}
