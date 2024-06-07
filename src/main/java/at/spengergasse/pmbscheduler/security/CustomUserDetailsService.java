package at.spengergasse.pmbscheduler.security;

import at.spengergasse.pmbscheduler.model.Person;
import at.spengergasse.pmbscheduler.model.Role;
import at.spengergasse.pmbscheduler.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private PersonRepository repo;

    @Autowired
    public CustomUserDetailsService(PersonRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = repo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new User(person.getEmail(), person.getPassword(), mapRolesToAuthorities(person.getRoles()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
