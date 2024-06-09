package at.spengergasse.pmbscheduler.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class StaticViewController {

    @GetMapping("/imprint")
    public String showImprint(Model model){
        model.addAttribute("roles", getAuthorities());
        return "imprint.html";
    }

    @GetMapping("terms-of-use")
    public String showTermsOfUser(Model model){
        model.addAttribute("roles", getAuthorities());
        return "terms-of-use.html";
    }

    @GetMapping("/about")
    public String showAbout(Model model){
        model.addAttribute("roles", getAuthorities());
        return "about.html";
    }

    @GetMapping("/")
    public String showHome(Model model) {
        model.addAttribute("roles", getAuthorities());
        return "index.html";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("roles", getAuthorities());
        return "register.html";
    }
    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("roles", getAuthorities());
        return "login.html";
    }

    @GetMapping("/user/profile")
    public String showProfile(Model model) {
        model.addAttribute("roles", getAuthorities());
        return "user/profile.html";
    }
    private List<String> getAuthorities() {
        List<String> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        System.out.println(roles);
        return roles;
    }
}
