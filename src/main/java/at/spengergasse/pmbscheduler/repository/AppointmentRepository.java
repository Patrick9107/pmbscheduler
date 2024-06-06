package at.spengergasse.pmbscheduler.repository;

import at.spengergasse.pmbscheduler.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByAddress (String address);
}
