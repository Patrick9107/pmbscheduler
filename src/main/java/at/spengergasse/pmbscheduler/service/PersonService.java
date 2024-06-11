package at.spengergasse.pmbscheduler.service;

import at.spengergasse.pmbscheduler.factory.PersonFactory;
import at.spengergasse.pmbscheduler.model.Appointment;
import at.spengergasse.pmbscheduler.model.Person;
import at.spengergasse.pmbscheduler.model.Role;
import at.spengergasse.pmbscheduler.model.VerificationToken;
import at.spengergasse.pmbscheduler.repository.PersonRepository;
import at.spengergasse.pmbscheduler.repository.RoleRepository;
import at.spengergasse.pmbscheduler.repository.VerificationTokenRepository;
import io.jsonwebtoken.security.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    private final PersonFactory personFactory;
    private PersonRepository personRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private VerificationTokenRepository tokenRepository;

    @Autowired
    public PersonService(PersonFactory personFactory, PersonRepository personRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, VerificationTokenRepository tokenRepository) {
        this.personFactory = personFactory;
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.tokenRepository = tokenRepository;
    }

    public List<Person> findAllPerson(){
        return personRepository.findAll();
    }

    public Optional<Person> findByEmail(String email){
        return personRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return personRepository.existsByEmail(email);
    };

    public List<Long> getCurrentUserAppointments(String userEmail) {
        List<Long> appointments = new ArrayList<>();
        findByEmail(userEmail).ifPresent(person -> {
            person.getAppointments().forEach(appointment ->  appointments.add(appointment.getId()));
        });
        return appointments;
    }
    public List<Person> findByFirstNameAndLastName(String firstName, String lastName ){
        return personRepository.findByFirstNameAndLastName(firstName, lastName).stream().toList();
    }

//    public List<Person> findByBirthDate(LocalDate birthDate){
//        return personRepository.findByBirthDate(birthDate).stream().toList();
//    }
    public List<Person> generateFakePersons(int count) {
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            persons.add(personFactory.createFakePerson());
            personRepository.save(persons.get(i));
        }
        return persons;
    }

    public List<Role> getUserRoles() {
        List<Role> roleList = new ArrayList<>();
        roleRepository.findByName("ROLE_USER").ifPresent(roleList::add);
        return roleList;
    }

    public void save(Person person){
        personRepository.save(person);
    }

}
