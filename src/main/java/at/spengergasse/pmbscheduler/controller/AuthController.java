package at.spengergasse.pmbscheduler.controller;

import at.spengergasse.pmbscheduler.model.Person;
import at.spengergasse.pmbscheduler.model.Role;
import at.spengergasse.pmbscheduler.repository.PersonRepository;
import at.spengergasse.pmbscheduler.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private PersonRepository personRepository;

    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;


    public AuthController(PersonRepository personRepository,
                          RoleRepository roleRepository,
                          AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder
    )
    {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

//    @PostMapping("login")
//    public String login(@ModelAttribute Person person, Model model) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(person.getEmail(), person.getPassword()));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            model.addAttribute("roles", authoritiesService.getAutorities());
//            return "redirect:/";
//        } catch (Exception e) {
//            model.addAttribute("error", "Invalid email or password");
//            return "/login";
//        }
//    }

    @PostMapping("login")
    public String login(@ModelAttribute Person person, Model model) {
        try {
            SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(grantedAuthority -> System.out.println("Security Context authorities before Authentication:" + grantedAuthority));
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(person.getEmail(), person.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(grantedAuthority -> System.out.println("Security Context authorities after Authentication:" + grantedAuthority));

            // Log to check authentication
            System.out.println("(Login) Authentication successful: " + authentication.isAuthenticated());
            System.out.println("(Login) Authentication Authorities: " + authentication.getAuthorities());

            // Now that authentication is set, fetch the authorities
            List<String> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            System.out.println("(Login) Retrieved roles: " + roles);
            model.addAttribute("roles", roles);
            return "redirect:/";
        } catch (Exception e) {
            System.out.println("Authentication failed " + e);
            model.addAttribute("error", "Invalid email or password");
            return "login"; // Assuming you have a login.html template
        }
    }

    @Transactional
    @PostMapping("register")
    public String register(@ModelAttribute Person newPerson, Model model) {
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
        roleRepository.findByName("ROLE_USER").ifPresent(role -> person.getRoles().add(role));
        personRepository.save(person);

        model.addAttribute("roles", SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return "redirect:../login";
    }
}
