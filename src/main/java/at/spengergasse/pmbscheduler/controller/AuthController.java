package at.spengergasse.pmbscheduler.controller;

import at.spengergasse.pmbscheduler.model.Person;
import at.spengergasse.pmbscheduler.model.Role;
import at.spengergasse.pmbscheduler.repository.PersonRepository;
import at.spengergasse.pmbscheduler.repository.RoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String login(HttpServletRequest request, @ModelAttribute Person person, Model model) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(person.getEmail(), person.getPassword()));
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            securityContext.getAuthentication().getAuthorities().forEach(grantedAuthority -> System.out.println("Security Context authorities after Authentication:" + grantedAuthority));

            // Http session to update security context
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

            model.addAttribute("roles", getAuthorities());
            return "redirect:/";
        } catch (Exception e) {
            System.out.println("Authentication failed " + e);
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    @Transactional
    @PostMapping("register")
    public String register(@ModelAttribute Person newPerson, Model model) {
        if (personRepository.existsByEmail(newPerson.getEmail())) {
            model.addAttribute("roles", getAuthorities());
            model.addAttribute("error", "Email is already in use!");
            return "register.html";
        }

        Person person = new Person();
        person.setEmail(newPerson.getEmail());
        person.setPassword(passwordEncoder.encode(newPerson.getPassword()));
        person.setFirstName(newPerson.getFirstName());
        person.setLastName(newPerson.getLastName());
        person.setBirthDate(newPerson.getBirthDate());
        personRepository.save(person);
        roleRepository.findByName("ROLE_USER").ifPresent(role -> person.getRoles().add(role));
        personRepository.save(person);

        model.addAttribute("roles", getAuthorities());
        return "redirect:../login";
    }

    private List<String> getAuthorities() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    }
}
