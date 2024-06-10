package at.spengergasse.pmbscheduler.controller;

import at.spengergasse.pmbscheduler.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class StaticViewController {

    private AuthService authService;

    @Autowired
    public StaticViewController(AuthService authService) {
        this.authService = authService;
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

    @GetMapping("/contact")
    public String showContact(Model model) {
        model.addAttribute("roles", authService.getAuthorities());
        return "contact.html";
    }
}
