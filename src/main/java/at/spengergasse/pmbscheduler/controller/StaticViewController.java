package at.spengergasse.pmbscheduler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticViewController {

    @GetMapping("/imprint")
    public String showImprint(){
        return "imprint.html";
    }

    @GetMapping("terms-of-use")
    public String showTermsOfUser(){
        return "terms-of-use.html";
    }

    @GetMapping("/about")
    public String showAbout(){
        return "about.html";
    }

    @GetMapping("/")
    public String showHome(){
        return "index.html";
    }

    @GetMapping("/register")
    public String showRegister() {
        return "register.html";
    }
    @GetMapping("/login")
    public String showLogin() {
        return "login.html";
    }
}
