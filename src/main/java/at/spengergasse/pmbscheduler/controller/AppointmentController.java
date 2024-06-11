package at.spengergasse.pmbscheduler.controller;

import at.spengergasse.pmbscheduler.model.Appointment;
import at.spengergasse.pmbscheduler.model.Person;
import at.spengergasse.pmbscheduler.repository.PersonRepository;
import at.spengergasse.pmbscheduler.service.AppointmentService;
import at.spengergasse.pmbscheduler.service.AuthService;
import at.spengergasse.pmbscheduler.service.PersonService;
import jakarta.persistence.Id;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class AppointmentController {

    private AppointmentService appointmentService;
    private PersonService personService;
    private AuthService authService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, PersonService personService, AuthService authService) {
        this.appointmentService = appointmentService;
        this.personService = personService;
        this.authService = authService;
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
//        appointmentService.generateFakeAppointments(20);

        System.out.println(authService.getAuthorities());

        model.addAttribute("roles", authService.getAuthorities());

        String loggedInUserEmail = authService.getLoggedInUserEmail();
        model.addAttribute("usersAppointments", personService.getCurrentUserAppointments(loggedInUserEmail));

        List<Appointment> appointments = appointmentService.getSortedAppointmentsAscSortByDateAndTime();
        model.addAttribute("appointments", appointments);

        List<Long> personSumForAppointment = appointmentService.getPersonSumForAppointment();
        model.addAttribute("personSumForAppointment", personSumForAppointment);

        return "events.html";
    }

    @GetMapping("/create-event")
    public String viewCreateEvent(Model model){

        model.addAttribute("roles", authService.getAuthorities());

        if(!appointmentService.getPersonSumForAppointment().isEmpty()){
            Long personSumForAppointment = appointmentService.getPersonSumForAppointment().getFirst();
            model.addAttribute("personSumForAppointment", personSumForAppointment);
        }


        Optional<Appointment> appointment = appointmentService.getNextAppointment();
        model.addAttribute("appointment", appointment.orElse(null));
        return "create-event.html";
    }

    @PostMapping("/create-event-post")
    public String createNewEvent(
            @RequestParam String description,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate appointmentDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time,
            @RequestParam String address,
            @RequestParam Integer availableSpots
    ){

        appointmentService.createAppointment(description, appointmentDate, time, address, availableSpots);
        return "redirect:/events";
    }

    //TODO
   @PostMapping("/apply-for-event")
    public String apply(@RequestParam Long id){
       //wenn ein appointment voll ist soll sich der user nicht mehr anmelden können.
        appointmentService.addApplyingUserToAppointment(id);
        return "redirect:/events";
   }

   // TODO TYP FÜR APPOINTMENTS MACHEN (PRÜFUNG, WORKSHOP, ETC.) UND FUNKTION UM NACH DENEN ZU SORTIEREN (WENN MOTIVATION)
}
