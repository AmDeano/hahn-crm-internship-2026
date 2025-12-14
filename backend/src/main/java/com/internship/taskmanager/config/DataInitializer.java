package com.internship.taskmanager.config;

import com.internship.taskmanager.model.User;
import com.internship.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${DEMO_USER_EMAIL:demo@hahn.com}")
    private String demoEmail;

    @Value("${DEMO_USER_PASSWORD:demo123}")
    private String demoPassword;

    @Value("${DEMO_USER_NAME:Demo User}")
    private String demoName;

    @Value("${DEMO_USER_EMAIL:test@hahn.com}")
    private String testEmail;

    @Value("${DEMO_USER_PASSWORD:test123}")
    private String testPassword;

    @Value("${DEMO_USER_NAME:test User}")
    private String testName;

    @Override
    public void run(String... args) throws Exception{
        if(userRepository.count()==0){
            User demo = User.builder()
                    .email(demoEmail)
                    .password(demoPassword)
                    .name(demoName)
                    .build();
            userRepository.save(demo);

            User test = User.builder()
                    .email(testEmail)
                    .password(testPassword)
                    .name(testName)
                    .build();
            userRepository.save(test);

            System.out.println("Demo users initialized successfully");
        }
    }
}
