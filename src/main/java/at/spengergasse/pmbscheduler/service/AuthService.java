package at.spengergasse.pmbscheduler.service;

import at.spengergasse.pmbscheduler.repository.PersonRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private PersonRepository personRepository;


    public List<String> getAuthorities() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    }

    public String getLoggedInUserEmail(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
