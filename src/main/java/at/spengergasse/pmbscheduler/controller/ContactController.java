package at.spengergasse.pmbscheduler.controller;

import at.spengergasse.pmbscheduler.service.AuthService;
import at.spengergasse.pmbscheduler.service.ContactService;
import at.spengergasse.pmbscheduler.service.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {
    AuthService authService;
    EmailSenderService emailSenderService;
    ContactService contactService;

    @Autowired
    public ContactController(AuthService authService, EmailSenderService emailSenderService, ContactService contactService) {
        this.authService = authService;
        this.emailSenderService = emailSenderService;
        this.contactService = contactService;
    }

    @GetMapping("/contact")
    public String showContact(Model model) {
        model.addAttribute("roles", authService.getAuthorities());
        return "contact.html";
    }

    @PostMapping("/submit-contact")
    public String sendEmail(
            @RequestParam String userEmail,
            @RequestParam String subject,
            @RequestParam String message
    ){
        contactService.createNewContact(userEmail,subject,message);
        emailSenderService.sendEmail("pmbscheduler@gmail.com",subject, message);
        return "redirect:/";
    }
}
