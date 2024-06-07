package at.spengergasse.pmbscheduler.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

//    public String generateToken(Authentication auth) {
//        String username = auth.getName();
//        Date currentDate = new Date();
//        Date expirationDate = new Date(currentDate.getTime() + 1000 * 60 * 60 * 24);
//
//        // import Jwt into maven 8:30
////        String token = Jwts.builder();
//    }
}
