package at.spengergasse.pmbscheduler.repository;

import at.spengergasse.pmbscheduler.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByFirstNameAndLastName(String firstName, String lastName);
    Optional<Person> findByBirthDate(LocalDate birthdate);
    Optional<Person> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("SELECT COUNT(p) FROM Person p JOIN p.appointments a WHERE a.id = :appointmentId")
    Long countPersonsByAppointmentId(@Param("appointmentId") Long appointmentId);
}
