package com.jlf.blog1.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutmeContro {
    @GetMapping("/aboutme")
    public String index(){

        // System.out.println("---index---");
        return "aboutme";
    }
}
