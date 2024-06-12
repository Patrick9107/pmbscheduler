package at.spengergasse.pmbscheduler.controller;

import at.spengergasse.pmbscheduler.service.AuthService;
import at.spengergasse.pmbscheduler.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;


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

    @GetMapping("/download-terms-of-use")
    public ResponseEntity<Resource> downloadTermsOfService() throws IOException {
        // Load the Terms of Service document from the resources directory
        Resource resource = new ClassPathResource("static/terms_of_service.pdf");

        // Set the appropriate HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=terms_of_service.pdf");

        // Return the document as a ResponseEntity
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
