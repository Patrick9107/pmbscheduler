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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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


    //TODO changes the route to a proper one!
    @GetMapping("/personData")
    public String returnPersonData(Model model){
        List<Person> persons = personService.generateFakePersons(10); // Generate 10 fake persons
        model.addAttribute("persons", persons);
        return "index.html";
    }
}
