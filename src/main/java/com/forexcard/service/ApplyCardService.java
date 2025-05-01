package com.forexcard.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.forexcard.dto.UserDetailsDTO;
import com.forexcard.model.User;
import com.forexcard.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplyCardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public void processCardApplication(Integer userId, UserDetailsDTO dto, MultipartFile file) throws IOException {
        // Check if the file is a PDF
        if (!file.getContentType().equals("application/pdf")) {
            throw new IllegalArgumentException("Only PDF files are allowed.");
        }

        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("File is too large. Maximum size is 5MB.");
        }

        byte[] documentBytes = file.getBytes();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setAddress(dto.getAddress());
        user.setState(dto.getState());
        user.setCountry(dto.getCountry());
        user.setGender(dto.getGender());
        user.setPhonenumber(dto.getPhonenumber());
        user.setDob(dto.getDob());
        user.setSalary(dto.getSalary());
        user.setPan(dto.getPan());
        user.setSalarySlip(documentBytes); // Store PDF as byte array
        user.setAdminAction("PENDING");

        userRepository.save(user);
        emailService.sendCardApplicationConfirmation(user.getEmail());
    }

    @Transactional(readOnly = true)
    public byte[] getSalarySlipByUserId(Integer userId) {
        return userRepository.findById(userId)
                .map(forexCard -> forexCard.getSalarySlip())
                .orElse(null);
    }
}
