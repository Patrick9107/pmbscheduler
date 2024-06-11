package at.spengergasse.pmbscheduler.controller;

import at.spengergasse.pmbscheduler.model.Person;
import at.spengergasse.pmbscheduler.service.AuthService;
import at.spengergasse.pmbscheduler.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class PersonController {

    private PersonService personService;

    private AuthService authService;

    @Autowired
    public PersonController(PersonService service, AuthService authService) {
        this.personService = service;
        this.authService = authService;
    }

    @GetMapping("/profile")
    public String getUserProfile(@RequestParam(value = "edit", required = false) String edit, Model model) {
        model.addAttribute("roles", authService.getAuthorities());
        personService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).ifPresentOrElse(
                person -> model.addAttribute("person", person),
                () -> model.addAttribute("person", null));
        if (edit != null) {
            model.addAttribute("mode", "edit");
            return "/user/profile.html";
        } else {
            model.addAttribute("mode", "view");
            return "/user/profile.html";
        }
    }

    @PostMapping("/update-profile")
    public String updateProfile(@RequestParam(value = "firstName", required = false) String firstName,
                                @RequestParam(value = "lastName", required = false) String lastName,
                                @RequestParam(value = "email", required = false) String email) {
        // todo check if params are not null / not blank etc
        Optional<Person> personOptional = personService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (personOptional.isPresent()) {
            if (firstName != null && !firstName.isBlank())
                personOptional.get().setFirstName(firstName);
            if (lastName != null && !lastName.isBlank())
                personOptional.get().setLastName(lastName);
            if (email != null && !email.isBlank())
                personOptional.get().setEmail(email);
            personService.save(personOptional.get());
        }
        return "redirect:profile";
    }
}
