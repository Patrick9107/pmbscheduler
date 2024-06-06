package at.spengergasse.pmbscheduler.service;

import at.spengergasse.pmbscheduler.factory.PersonFactory;
import at.spengergasse.pmbscheduler.model.Person;
import at.spengergasse.pmbscheduler.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    private final PersonFactory personFactory;
    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository, PersonFactory personFactory) {
        this.personRepository = personRepository;
        this.personFactory = personFactory;
    }

    public List<Person> findAllPerson(){
        return personRepository.findAll();
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
}
