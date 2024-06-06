package at.spengergasse.pmbscheduler.factory;

import at.spengergasse.pmbscheduler.model.Person;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Random;

@Component
public class PersonFactory {

    private final Faker faker = new Faker();
    private final Random random = new Random();

    public Person createFakePerson() {
        return Person.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .birthDate(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .isVerfied(random.nextBoolean())
                .build();
    }
}
