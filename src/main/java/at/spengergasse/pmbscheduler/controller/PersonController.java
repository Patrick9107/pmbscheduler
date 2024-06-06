package at.spengergasse.pmbscheduler.controller;

import at.spengergasse.pmbscheduler.model.Person;
import at.spengergasse.pmbscheduler.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService service) {
        this.personService = service;
    }

    @GetMapping("/")
    public String returnPersonData(Model model){
        List<Person> persons = personService.generateFakePersons(10); // Generate 10 fake persons
        model.addAttribute("persons", persons);
        return "index.html";
    }
}
