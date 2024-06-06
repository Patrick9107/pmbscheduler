package at.spengergasse.pmbscheduler.controller;

import at.spengergasse.pmbscheduler.model.Appointment;
import at.spengergasse.pmbscheduler.model.Person;
import at.spengergasse.pmbscheduler.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AppointmentController {

    private AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

//    TODO FÜR ECHTE DATEN
//    @GetMapping("/events")
//    public String listAppointments(Model model){
//        List<Appointment> appointments = appointmentService.findAll();
//        model.addAttribute("appointments", appointments);
//        return "events.html";
//    }

// FÜR FAKE DATEN
    @GetMapping("/events")
    public String returnAppointmentData(Model model){
        List<Appointment> appointments = appointmentService.generateFakeAppointments(10);
        model.addAttribute("appointments", appointments);
        return "events.html";
    }

    @GetMapping("/create-event")
    public String viewCreateEvent(){
        return "create-event.html";
    }
}
