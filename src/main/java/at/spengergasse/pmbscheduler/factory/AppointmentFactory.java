package at.spengergasse.pmbscheduler.factory;

import at.spengergasse.pmbscheduler.model.Appointment;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.Date;

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


    //TESTS THE findAllFutureAppointmentsOrderedByDateAndTime query
//public Appointment createFakeAppointment() {
//    // Generate start and end dates as java.util.Date objects
//    Date startDate = Date.from(LocalDate.of(2024, 6, 4).atStartOfDay(ZoneId.systemDefault()).toInstant());
//    Date endDate = Date.from(LocalDate.of(2024, 6, 19).atStartOfDay(ZoneId.systemDefault()).toInstant());
//
//    // Generate a random date between 4th June 2024 and 19th June 2024
//    Date randomDate = faker.date().between(startDate, endDate);
//
//    // Convert the random date back to LocalDate
//    LocalDate appointmentDate = randomDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//
//    return Appointment.builder()
//            .description(faker.lorem().sentence())
//            .appointmentDate(appointmentDate)
//            .time(LocalTime.of(faker.number().numberBetween(0, 23), faker.number().numberBetween(0, 59)))
//            .address(faker.address().fullAddress())
//            .build();
//}

}