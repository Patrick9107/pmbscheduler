package at.spengergasse.pmbscheduler.controller;

import at.spengergasse.pmbscheduler.model.Person;
import at.spengergasse.pmbscheduler.repository.PersonRepository;
import at.spengergasse.pmbscheduler.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PersonController {

    private PersonService service;

    @Autowired
    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String returnPersonData(Model model){
        List<Person> persons = service.generateFakePersons(10); // Generate 10 fake persons
        model.addAttribute("persons", persons);
        return "index.html";
    }
}
