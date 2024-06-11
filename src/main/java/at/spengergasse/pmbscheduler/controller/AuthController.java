package at.spengergasse.pmbscheduler.controller;

import at.spengergasse.pmbscheduler.model.Person;
import at.spengergasse.pmbscheduler.model.VerificationToken;
import at.spengergasse.pmbscheduler.service.AuthService;
import at.spengergasse.pmbscheduler.service.EmailSenderService;
import at.spengergasse.pmbscheduler.service.PersonService;
import at.spengergasse.pmbscheduler.service.VerificationTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private EmailSenderService senderService;
    private PersonService personService;
    private AuthenticationManager authenticationManager;
    private AuthService authService;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenService tokenService;

    @Autowired
    public AuthController(EmailSenderService senderService,
                          PersonService personService,
                          AuthenticationManager authenticationManager,
                          AuthService authService,
                          PasswordEncoder passwordEncoder,
                          VerificationTokenService tokenService) {
        this.senderService = senderService;
        this.personService = personService;
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @PostMapping("login")
    public String login(HttpServletRequest request, @ModelAttribute Person person, Model model) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(person.getEmail(), person.getPassword()));
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            // Http session to update security context
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            model.addAttribute("roles", authService.getAuthorities());
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
        if (personService.existsByEmail(newPerson.getEmail())) {
            model.addAttribute("roles", authService.getAuthorities());
            model.addAttribute("error", "Email is already in use!");
            return "register.html";
        }
        Person person = new Person();
        person.setEmail(newPerson.getEmail());
        person.setPassword(passwordEncoder.encode(newPerson.getPassword()));
        person.setFirstName(newPerson.getFirstName());
        person.setLastName(newPerson.getLastName());
        person.setBirthDate(newPerson.getBirthDate());
        personService.getUserRoles().forEach(role -> person.getRoles().add(role));
        personService.save(person);
        senderService.sendEmail(person.getEmail(),
                "PMB-Scheduler Account Verification",
                String.format("Hello %s %s,%n%n"
                                + "Thank you for registering with PMB-Scheduler!%n%n"
                                + "To complete your registration, please confirm your email address by clicking the link below:%n"
                                + "http://localhost:8080/auth/verify?token=%s%n%n"
                                + "Please note: This link will expire in 15 minutes.%n%n"
                                + "Best regards,%n"
                                + "The PMB-Scheduler Team",
                        person.getLastName(), person.getFirstName(), tokenService.createVerificationToken(person))
        );
        return "redirect:../login";
    }
    @GetMapping("/verify")
    public String verify(@RequestParam("token") String token) {
        Optional<Person> personOptional = tokenService.getPersonByVerificationToken(token);
        if (token == null || personOptional.isEmpty()) {
            return "e";
        }

        if (personOptional.get().getIsVerfied()) {
            // already verified
            return "redirect:../";
        }
        // todo if token already expired
        // if token match
        Optional<VerificationToken> tokenOptional = tokenService.getVerificationTokenByPersonId(personOptional.get().getId());
        if (tokenOptional.isPresent()) {
            personOptional.get().setIsVerfied(true);
            tokenOptional.get().setConfirmedAt(LocalDateTime.now());
            personService.save(personOptional.get());
            tokenService.saveToken(tokenOptional.get());
        }
        return "redirect:../";
    }
}