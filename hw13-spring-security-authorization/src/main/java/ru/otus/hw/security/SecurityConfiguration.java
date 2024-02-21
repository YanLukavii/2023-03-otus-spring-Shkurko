package ru.otus.hw.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.otus.hw.services.UserSecurityDataService;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration  {

    private final UserSecurityDataService userSecurityDataService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login", "/access-denied").permitAll();
                    authorize.requestMatchers(HttpMethod.GET,"/", "/create").hasAnyRole("ADMIN", "USER");
                    authorize.requestMatchers(HttpMethod.POST,"/", "/create").hasAnyRole("ADMIN", "USER");
                    authorize.requestMatchers("/**").hasAnyRole("ADMIN");
                    authorize.anyRequest().denyAll();
                })
                .userDetailsService(userSecurityDataService)
                .formLogin(Customizer.withDefaults())
                .exceptionHandling((exceptionHandling) -> exceptionHandling
                        .accessDeniedPage("/access-denied")
                );



        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}