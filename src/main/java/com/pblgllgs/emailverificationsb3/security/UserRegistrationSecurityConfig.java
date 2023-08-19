package com.pblgllgs.emailverificationsb3.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class UserRegistrationSecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .cors(Customizer.withDefaults())
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register/**").permitAll()
                        .requestMatchers("/users").hasAnyAuthority("USER", "ADMIN")
                )
                .formLogin().and().build();
    }
}
