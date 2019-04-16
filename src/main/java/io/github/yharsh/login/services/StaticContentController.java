package io.github.yharsh.login.services;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class StaticContentController {
    @RequestMapping("/login")
    public String loginPage(Map<String, Object> model) {
        return "index.html";
    }

    @RequestMapping("/")
    public String defaultPage(Map<String, Object> model) {
        return "index.html";
    }

    @RequestMapping("/register")
    public String registerPage(Map<String, Object> model) {
        return "index.html";
    }
}
