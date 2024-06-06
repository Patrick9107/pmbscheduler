package at.spengergasse.pmbscheduler.repository;

import at.spengergasse.pmbscheduler.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByAddress (String address);
    List<Appointment> findByAppointmentDateAfterOrderByAppointmentDateAsc(LocalDate currentDate);
    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate > CURRENT_DATE OR (a.appointmentDate = CURRENT_DATE AND a.time > CURRENT_TIME) ORDER BY a.appointmentDate ASC, a.time ASC")
    List<Appointment> findAllFutureAppointmentsOrderedByDateAndTime();
    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate > CURRENT_DATE OR (a.appointmentDate = CURRENT_DATE AND a.time > CURRENT_TIME) ORDER BY a.appointmentDate ASC, a.time ASC LIMIT 1")
    Optional<Appointment> findNextAppointment();


}
