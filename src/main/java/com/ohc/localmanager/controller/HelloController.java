package com.ohc.localmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.Map;

@RestController
@RequestMapping("/api")
class ApiHelloController {

    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of("message", "Hello, Spring Boot!");
    }
}

@Controller
class JspController {

    @GetMapping("/api/jsp")
    public String helloJsp(Model model) {
        model.addAttribute("message", "Hello from JSP!");
        return "hello";
    }
}
