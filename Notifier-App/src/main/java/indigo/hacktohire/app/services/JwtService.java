package indigo.hacktohire.app.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import indigo.hacktohire.app.configurations.JwtProperties;
import indigo.hacktohire.app.entities.Airline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@Service
public class JwtService {

    @Autowired
    JwtProperties properties;

    public String issueJWT(Airline airline){
        return JWT.create()
                .withSubject(String.valueOf(airline.getId()))
                .withClaim("authorities", Arrays.asList("SCOPE_AIRLINE_ADMIN"))
                .withExpiresAt(Instant.now().plus(30, ChronoUnit.DAYS))
                .sign(Algorithm.HMAC256(properties.getSecretKey()));
    }

    public DecodedJWT decodeJWT(String token){
        return JWT.require(Algorithm.HMAC256(properties.getSecretKey())).build().verify(token);
    }

}
