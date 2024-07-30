package indigo.hacktohire.app.converters;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import indigo.hacktohire.app.entities.Airline;
import indigo.hacktohire.app.security.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtToUserPrincipalConverter {

    public UserPrincipal convert(DecodedJWT decodedJWT){

        Airline airline = new Airline();
        airline.setId(Integer.parseInt(decodedJWT.getSubject()));

        return UserPrincipal.builder()
                .airline(airline)
                .authorities(extractAuthorities(decodedJWT))
                .build();
    }

    private List<SimpleGrantedAuthority> extractAuthorities(DecodedJWT decodedJWT){
        Claim claim = decodedJWT.getClaim("authorities");
        if(claim.isNull() || claim.isMissing()){
            return List.of();
        }
        return claim.asList(SimpleGrantedAuthority.class);
    }

}
