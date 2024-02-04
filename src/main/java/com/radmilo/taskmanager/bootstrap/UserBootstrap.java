package com.radmilo.taskmanager.bootstrap;

import com.radmilo.taskmanager.entity.User;
import com.radmilo.taskmanager.enumeration.Role;
import com.radmilo.taskmanager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserBootstrap implements CommandLineRunner {

    private final UserRepository userRepository;

    public UserBootstrap(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(new User("radenikolic@yahoo.com", "$2a$12$qGkaL1IeWG6clrMQzuzU1e.eVqNPJFiQrXbU9e0Y5hjgydKvb8Gvy", Role.ADMIN));
        userRepository.save(new User("dejanilic@yahoo.com", "$2a$12$CF1DzFM2xJ/TzfhG6Ex.iuFm9TeLA8f03n2Eex6xk1XkZtUmhr90m", Role.USER));
    }
}
