package at.spengergasse.pmbscheduler.service;

import at.spengergasse.pmbscheduler.factory.AppointmentFactory;
import at.spengergasse.pmbscheduler.model.Appointment;
import at.spengergasse.pmbscheduler.repository.AppointmentRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}

