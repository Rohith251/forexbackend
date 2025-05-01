package com.forexcard.dataload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.forexcard.model.User;
import com.forexcard.repo.UserRepository;

@Component
public class AdminAuditorSet implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepo;

    public AdminAuditorSet(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userRepo.existsByEmail("admin@gmail.com")) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin"));  // Set and hash a real password
            admin.setRole("admin");
            userRepo.save(admin);
        }

        if (!userRepo.existsByEmail("auditor@gmail.com")) {
            User auditor = new User();
            auditor.setName("Auditor");
            auditor.setEmail("auditor@gmail.com");
            auditor.setPassword(passwordEncoder.encode("auditor"));  // Set and hash a real password
            auditor.setRole("auditor");
            userRepo.save(auditor);
        }
    }
}
