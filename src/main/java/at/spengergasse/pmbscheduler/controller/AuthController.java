package at.spengergasse.pmbscheduler.controller;

import at.spengergasse.pmbscheduler.model.Person;
import at.spengergasse.pmbscheduler.model.Role;
import at.spengergasse.pmbscheduler.repository.PersonRepository;
import at.spengergasse.pmbscheduler.repository.RoleRepository;
import at.spengergasse.pmbscheduler.service.AuthoritiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private PersonRepository personRepository;

    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    private AuthoritiesService authoritiesService;

    public AuthController(PersonRepository personRepository,
                          RoleRepository roleRepository,
                          AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder,
                          AuthoritiesService authoritiesService
    )
    {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.authoritiesService = authoritiesService;
    }

    @PostMapping("login")
    public String login(@ModelAttribute Person person, Model model) {
        model.addAttribute("roles", authoritiesService.getAutorities());
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(person.getEmail(), person.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", "Invalid email or password");
            return "/login";
        }
    }

    @Transactional
    @PostMapping("register")
    public String register(@ModelAttribute Person newPerson, Model model) {
        model.addAttribute("roles", authoritiesService.getAutorities());
        if (personRepository.existsByEmail(newPerson.getEmail())) {
            model.addAttribute("error", "Email is already in use!");
            return "register.html";
        }

        Person person = new Person();
        person.setEmail(newPerson.getEmail());
        person.setPassword(passwordEncoder.encode(newPerson.getPassword()));
        person.setFirstName(newPerson.getFirstName());
        person.setLastName(newPerson.getLastName());
        personRepository.save(person);
        roleRepository.findByName("USER").ifPresent(role -> person.getRoles().add(role));
        personRepository.save(person);

        return "redirect:../login";
    }
}
