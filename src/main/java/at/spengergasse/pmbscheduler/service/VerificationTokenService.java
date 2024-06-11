package at.spengergasse.pmbscheduler.service;

import at.spengergasse.pmbscheduler.model.Person;
import at.spengergasse.pmbscheduler.model.VerificationToken;
import at.spengergasse.pmbscheduler.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class VerificationTokenService {

    private VerificationTokenRepository tokenRepository;

    @Autowired
    public VerificationTokenService(VerificationTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String createVerificationToken(Person person) {
        String uuidToken = UUID.randomUUID().toString().replaceAll("-", "");
        VerificationToken token = new VerificationToken();
        token.setPerson(person);
        token.setToken(uuidToken);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        tokenRepository.save(token);
        return uuidToken;
    }

    public Optional<VerificationToken> getVerificationTokenbyToken(String token) {
        return tokenRepository.getVerificationTokenByToken(token);
    }

    public Optional<Person> getPersonByVerificationToken(String token) {
        return tokenRepository.getPersonByVerificationToken(token);
    }

    public Optional<VerificationToken> getVerificationTokenByPersonId(Long id) {
        return tokenRepository.getVerificationTokenByPersonId(id);
    }

    public void saveToken(VerificationToken token) {
        tokenRepository.save(token);
    }
}
