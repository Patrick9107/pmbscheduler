package at.spengergasse.pmbscheduler.service;

import at.spengergasse.pmbscheduler.factory.AppointmentFactory;
import at.spengergasse.pmbscheduler.model.Appointment;
import at.spengergasse.pmbscheduler.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private AppointmentRepository appointmentRepository;
    private AppointmentFactory appointmentFactory;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, AppointmentFactory appointmentFactory) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentFactory = appointmentFactory;   

    }

    public List<Appointment> findAll(){
        return appointmentRepository.findAll();
    }

    public List<Appointment> findByAddress (String address){
        return appointmentRepository.findByAddress(address).stream().toList();
    }

    public List<Appointment> generateFakeAppointments(int count) {
        List<Appointment> appointments = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            appointments.add(appointmentFactory.createFakeAppointment());
            appointmentRepository.save(appointments.get(i));
        }
        return appointments;
    }
    public List<Appointment> getSortedAppointmentsAsc() {
        LocalDate currentDate = LocalDate.now();
        return appointmentRepository.findByAppointmentDateAfterOrderByAppointmentDateAsc(currentDate);
    }

    public List<Appointment> getSortedAppointmentsAscSortByDateAndTime(){
        return appointmentRepository.findAllFutureAppointmentsOrderedByDateAndTime();
    }

    public Optional<Appointment> getNextAppointment(){
        return appointmentRepository.findNextAppointment();
    }

    public Appointment createAppointment(String description, LocalDate appointmentDate, LocalTime time, String address){
        Appointment appointment = Appointment.builder()
                .description(description)
                .appointmentDate(appointmentDate)
                .time(time)
                .address(address)
                .build();

        return appointmentRepository.save(appointment);
    }


}

