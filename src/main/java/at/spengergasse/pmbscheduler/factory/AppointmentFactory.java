package at.spengergasse.pmbscheduler.factory;

import at.spengergasse.pmbscheduler.model.Appointment;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

@Component
public class AppointmentFactory {

    private final Faker faker = new Faker();

    public Appointment createFakeAppointment() {
        return Appointment.builder()
                .description(faker.lorem().sentence())
                .appointmentDate(faker.date().future(30, java.util.concurrent.TimeUnit.DAYS).toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate())
                .time(LocalTime.of(faker.number().numberBetween(0, 23), faker.number().numberBetween(0, 59)))
                .address(faker.address().fullAddress())
                .build();
    }

}