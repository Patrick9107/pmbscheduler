package at.spengergasse.pmbscheduler.factory;

import at.spengergasse.pmbscheduler.model.Appointment;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class AppointmentFactory {

    private final Faker faker = new Faker();

    public Appointment createFakeAppointment() {
        return Appointment.builder()
                .description(faker.lorem().sentence())
                .appointmentDateTime(faker.date().future(30, java.util.concurrent.TimeUnit.DAYS).toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDateTime())
                .address(faker.address().fullAddress())
                .build();
    }
}