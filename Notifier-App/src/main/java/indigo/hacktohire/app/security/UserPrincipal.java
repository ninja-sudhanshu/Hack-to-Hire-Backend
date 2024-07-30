package indigo.hacktohire.app.security;

import indigo.hacktohire.app.entities.Airline;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {

    Airline airline;
    Collection<? extends GrantedAuthority> authorities;

    UserPrincipal(Airline airline){
        super();
        this.airline = airline;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "SCOPE_AIRLINE_ADMIN";
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
        return List.of(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return airline.getPassword();
    }

    @Override
    public String getUsername() {
        return airline.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}