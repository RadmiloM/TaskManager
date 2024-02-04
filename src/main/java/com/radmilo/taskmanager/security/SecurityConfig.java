package com.radmilo.taskmanager.security;

import com.radmilo.taskmanager.user.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    private static final String TASK_URL = "/api/v1/tasks";

    private static final String USER_ROLE = "USER";

    private static final String ADMIN_ROLE = "ADMIN";

    private static final String URI = "/{id}";

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .authorizeHttpRequests(request -> {
                    try {
                        request.requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers(HttpMethod.GET, TASK_URL).hasAnyAuthority(USER_ROLE, ADMIN_ROLE)
                                .requestMatchers(HttpMethod.GET, TASK_URL + URI).hasAnyAuthority(USER_ROLE, ADMIN_ROLE)
                                .requestMatchers(HttpMethod.POST, TASK_URL).hasAnyAuthority(USER_ROLE, ADMIN_ROLE)
                                .requestMatchers(HttpMethod.DELETE, TASK_URL + URI).hasAuthority(ADMIN_ROLE)
                                .requestMatchers(HttpMethod.PUT, TASK_URL + URI).hasAuthority(ADMIN_ROLE)
                                .anyRequest().authenticated().and().formLogin().and().httpBasic();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                });
        http.headers().frameOptions().disable();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

}
