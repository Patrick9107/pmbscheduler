package at.spengergasse.pmbscheduler.controller;

import at.spengergasse.pmbscheduler.model.Appointment;
import at.spengergasse.pmbscheduler.service.AppointmentService;
import at.spengergasse.pmbscheduler.service.AuthoritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
public class AppointmentController {

    private AppointmentService appointmentService;

    private AuthoritiesService authoritiesService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, AuthoritiesService authoritiesService) {
        this.appointmentService = appointmentService;
        this.authoritiesService = authoritiesService;
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
        model.addAttribute("roles", authoritiesService.getAutorities());
        //appointmentService.generateFakeAppointments(10);
        //List <Appointment> appointments = appointmentService.getSortedAppointmentsAsc();
        List<Appointment> appointments = appointmentService.getSortedAppointmentsAscSortByDateAndTime();
        model.addAttribute("appointments", appointments);
        return "events.html";
    }

    @GetMapping("/create-event")
    public String viewCreateEvent(Model model){
        model.addAttribute("roles", authoritiesService.getAutorities());
        Optional<Appointment> appointment = appointmentService.getNextAppointment();
        model.addAttribute("appointment", appointment.orElse(null));
        return "create-event.html";
    }

    @PostMapping("/create-event-post")
    public String createNewEvent(
            @RequestParam String description,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate appointmentDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time,
            @RequestParam String address
    ){

        appointmentService.createAppointment(description, appointmentDate, time, address);
        return "redirect:/events";
    }
}
