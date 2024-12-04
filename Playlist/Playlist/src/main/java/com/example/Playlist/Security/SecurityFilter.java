package com.example.Playlist.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityFilter {



@Bean
SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(request -> request.requestMatchers("/music/**").authenticated())
            .httpBasic(Customizer.withDefaults())
            .build();

}



@Bean
PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}

    @Bean
    UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder) {
        User.UserBuilder users = User.builder();
        UserDetails sarah = users
                .username("sarah")
                .password(passwordEncoder.encode("abc123"))
                .roles()
                .build();
        UserDetails carlos = users
                .username("carlos")
                .password(passwordEncoder.encode("qrs456"))
                .roles()
                .build();
        UserDetails kumar = users
                .username("kumar")
                .password(passwordEncoder.encode("xyz789"))
                .roles()
                .build();

        return new InMemoryUserDetailsManager(sarah , carlos, kumar );
    }

}
