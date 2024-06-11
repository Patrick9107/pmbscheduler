package at.spengergasse.pmbscheduler.service;

import at.spengergasse.pmbscheduler.model.Contact;
import at.spengergasse.pmbscheduler.repository.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContactService {
    private ContactRepository contactRepository;

    public Contact createNewContact(String userEmail, String subject, String message){
        Contact contact = Contact.builder()
                .userEmail(userEmail)
                .subject(subject)
                .message(message)
                .build();

        return contactRepository.save(contact);
    }

}
