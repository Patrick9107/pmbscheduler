package at.spengergasse.pmbscheduler.service;

import at.spengergasse.pmbscheduler.model.Appointment;
import at.spengergasse.pmbscheduler.repository.AppointmentRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    private AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> findAll(){
        return appointmentRepository.findAll();
    }

    public List<Appointment> findByAddress (String address){
        return appointmentRepository.findByAddress(address).stream().toList();
    }

}

