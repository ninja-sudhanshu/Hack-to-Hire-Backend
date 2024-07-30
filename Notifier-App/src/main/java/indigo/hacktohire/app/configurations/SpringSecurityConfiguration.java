package indigo.hacktohire.app.configurations;

import indigo.hacktohire.app.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SpringSecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.formLogin(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests((authorizeHttpRequests) -> {
            authorizeHttpRequests.requestMatchers("/airlines/signup").permitAll();
            authorizeHttpRequests.requestMatchers("/airlines/login").permitAll();
            authorizeHttpRequests.requestMatchers("/flights/all").permitAll();
            authorizeHttpRequests.requestMatchers("/booking").permitAll();
            authorizeHttpRequests.requestMatchers(HttpMethod.PATCH).permitAll();
            authorizeHttpRequests.anyRequest().authenticated();
        });
        httpSecurity.sessionManagement((sessionManagement) -> {
           sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        return httpSecurity.build();
    }

}