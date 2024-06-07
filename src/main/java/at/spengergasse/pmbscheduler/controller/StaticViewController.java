package at.spengergasse.pmbscheduler.controller;

import at.spengergasse.pmbscheduler.service.AuthoritiesService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StaticViewController {

    private AuthoritiesService authoritiesService;

    public StaticViewController(AuthoritiesService authoritiesService) {
        this.authoritiesService = authoritiesService;
    }

    @GetMapping("/imprint")
    public String showImprint(Model model){
        authorities(model);
        return "imprint.html";
    }

    @GetMapping("terms-of-use")
    public String showTermsOfUser(Model model){
        authorities(model);
        return "terms-of-use.html";
    }

    @GetMapping("/about")
    public String showAbout(Model model){
        authorities(model);
        return "about.html";
    }

    @GetMapping("/")
    public String showHome(Model model) {
        authorities(model);
        return "index.html";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        authorities(model);
        return "register.html";
    }
    @GetMapping("/login")
    public String showLogin(Model model) {
        authorities(model);
        return "login.html";
    }

    private void authorities(Model model) {
        List<String> authorities = authoritiesService.getAutorities();
        model.addAttribute("roles", authorities);
        // todo sout only for testing can be removed when we are done
        authorities.forEach(s -> System.out.println("Roles: " + s));
    }
}
