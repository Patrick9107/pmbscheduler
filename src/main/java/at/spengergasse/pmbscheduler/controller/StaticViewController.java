package at.spengergasse.pmbscheduler.controller;

import at.spengergasse.pmbscheduler.service.AuthService;
import at.spengergasse.pmbscheduler.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class StaticViewController {

    private AuthService authService;
    private EmailSenderService emailSenderService;

    @Autowired
    public StaticViewController(AuthService authService, EmailSenderService emailSenderService) {
        this.authService = authService;
        this.emailSenderService = emailSenderService;
    }

    @GetMapping("/imprint")
    public String showImprint(Model model){
        model.addAttribute("roles", authService.getAuthorities());
        return "imprint.html";
    }

    @GetMapping("terms-of-use")
    public String showTermsOfUser(Model model){
        model.addAttribute("roles", authService.getAuthorities());
        return "terms-of-use.html";
    }

    @GetMapping("/about")
    public String showAbout(Model model){
        model.addAttribute("roles", authService.getAuthorities());
        return "about.html";
    }

    @GetMapping("/")
    public String showHome(Model model) {
        model.addAttribute("roles", authService.getAuthorities());
        return "index.html";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("roles", authService.getAuthorities());
        return "register.html";
    }
    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("roles", authService.getAuthorities());
        return "login.html";
    }

}
