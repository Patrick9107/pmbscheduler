package at.spengergasse.pmbscheduler.repository;

import at.spengergasse.pmbscheduler.model.Person;
import at.spengergasse.pmbscheduler.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> getVerificationTokenByToken(String token);
    @Query("SELECT v.person FROM VerificationToken v WHERE v.token = :token")
    Optional<Person> getPersonByVerificationToken(@Param("token") String token);

    Optional<VerificationToken> getVerificationTokenByPersonId(Long personId);
}
