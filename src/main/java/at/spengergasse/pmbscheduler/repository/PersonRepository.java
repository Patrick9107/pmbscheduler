package at.spengergasse.pmbscheduler.repository;

import at.spengergasse.pmbscheduler.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByFirstNameAndLastName(String firstName, String lastName);
    Optional<Person> findByBirthDate (LocalDate birthdate);
}
