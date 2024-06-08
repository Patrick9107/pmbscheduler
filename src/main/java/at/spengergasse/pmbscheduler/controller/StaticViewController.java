package at.spengergasse.pmbscheduler.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StaticViewController {

    @GetMapping("/imprint")
    public String showImprint(){
        return "imprint.html";
    }

    @GetMapping("terms-of-use")
    public String showTermsOfUser(){;
        return "terms-of-use.html";
    }

    @GetMapping("/about")
    public String showAbout(){
        return "about.html";
    }

    @GetMapping("/")
    public String showHome() {
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

//    private void authorities(Model model) {
//        List<String> authorities = authoritiesService.getAutorities();
//        model.addAttribute("roles", authorities);
//        // todo sout only for testing can be removed when we are done
//        authorities.forEach(s -> System.out.println("Roles: " + s));
//    }
}
