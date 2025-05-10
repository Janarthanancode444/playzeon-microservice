package org.application.config;

import org.application.filter.JwtAuthFilter;
import org.application.service.CommonUserInfoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class CommonSecurityConfig {
    @Autowired
    private JwtAuthFilter authFilter;

    //    @Bean(name = "userModuleUserDetailsService")
    //authentication
    public UserDetailsService userDetailsService() {
        return new CommonUserInfoUserDetailsService();
    }


    //    @Bean(name = "commonSecurityFilterChain")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())


                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/image/**").hasAnyAuthority("ROLE_CENTER_ADMIN")
                        .requestMatchers("/api/v1/organization/**").hasAnyAuthority("ROLE_SUPER_ADMIN")
                        .requestMatchers("/api/v1/center/**").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/v1/sports/**").permitAll()
                        .requestMatchers("/api/v1/sportscentermap/**").permitAll()

                        .anyRequest().authenticated()).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session management
                ).authenticationProvider(authenticationProvider()) // Custom authentication provider
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class); // Add custom filter

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();

    }

}