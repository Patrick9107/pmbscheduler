package at.spengergasse.pmbscheduler.controller;

import at.spengergasse.pmbscheduler.dto.LoginDto;
import at.spengergasse.pmbscheduler.dto.RegisterDto;
import at.spengergasse.pmbscheduler.model.Person;
import at.spengergasse.pmbscheduler.model.Role;
import at.spengergasse.pmbscheduler.repository.PersonRepository;
import at.spengergasse.pmbscheduler.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private PersonRepository personRepository;

    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    public AuthController(
            PersonRepository personRepository,
            RoleRepository roleRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder)
    {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "login", consumes = {"application/json", "application/x-www-form-urlencoded"})
    public ResponseEntity<String> login(@ModelAttribute LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed in successfully", HttpStatus.OK);
    }

    @PostMapping(value = "register", consumes = {"application/json", "application/x-www-form-urlencoded"})
    public ResponseEntity<String> register(@ModelAttribute RegisterDto registerDto) {
        if (personRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email is already in use!", HttpStatus.BAD_REQUEST);
        }
        Person person = new Person();
        person.setEmail(registerDto.getEmail());
        person.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        person.setFirstName(registerDto.getFirstName());
        person.setLastName(registerDto.getLastName());

        // hier implementieren ob ein spezifischer user zb admin sein soll
        Role role = roleRepository.findByName("USER").get();
        person.setRoles(Collections.singletonList(role));

        personRepository.save(person);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
