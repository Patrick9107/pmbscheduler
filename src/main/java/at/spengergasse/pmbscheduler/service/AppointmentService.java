package at.spengergasse.pmbscheduler.service;

import at.spengergasse.pmbscheduler.factory.AppointmentFactory;
import at.spengergasse.pmbscheduler.model.Appointment;
import at.spengergasse.pmbscheduler.repository.AppointmentRepository;
import at.spengergasse.pmbscheduler.repository.PersonRepository;
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
    private PersonRepository personRepository;
    private PersonService personService;
    private AuthService authService;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, AppointmentFactory appointmentFactory, PersonRepository personRepository, PersonService personService, AuthService authService) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentFactory = appointmentFactory;
        this.personRepository =personRepository;
        this.personService = personService;
        this.authService = authService;
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

    public Optional<Appointment> getById(Long Id){
        return appointmentRepository.findById(Id);
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
    public Long getPersonCountForAppointment(Long appointmentId) {
        return personRepository.countPersonsByAppointmentId(appointmentId);
    }

    public List<Long> getPersonSumForAppointment(){
        List<Long> personSumForAppointment = new ArrayList<>();
         getSortedAppointmentsAscSortByDateAndTime().forEach(appointment -> personSumForAppointment.add(getPersonCountForAppointment(appointment.getId())));
         return personSumForAppointment;
    }

    public void addApplyingUserToAppointment(Long id){
        String loggedInUserEmail = authService.getLoggedInUserEmail();

        getById(id).ifPresent(appointment -> personService.findByEmail(loggedInUserEmail).ifPresent( person -> {
            Integer maxSlots =  0;

            if (getById(id).isPresent()){
                maxSlots = getById(id).get().getAvailableSpots();
            }

            Long personCountForAppointment = getPersonCountForAppointment(id);
            // +1 für den User der sich jetzt anmelden möchte
            if(!person.getAppointments().contains(appointment) && personCountForAppointment + 1 <= maxSlots ){
                person.getAppointments().add(appointment);
                personService.save(person);
            }

        }));
    }
}

