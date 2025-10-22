package com.example.mailgunservice.service;

import com.example.mailgunservice.model.User;
import com.example.mailgunservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MailgunEmailService emailService;

    @Transactional
    public User registerUser(String fullName, String email) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered.");
        }

        String otp = generateOtp();

        // 1. Save User with OTP
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setOtp(otp);
        User savedUser = userRepository.save(user);

        // 2. Send OTP Email using Mailgun
        sendOtpEmail(email, fullName, otp);

        return savedUser;
    }

    private String generateOtp() {
        // Generates a 6-digit numeric OTP
        return String.format("%06d", new Random().nextInt(999999));
    }

    private void sendOtpEmail(String to, String name, String otp) {
        String subject = "Your One-Time Password (OTP) ";
        String body = """
            Hello %s,

            Thank you for using this service!
            
            Your One-Time Password (OTP) is: %s
            
            Please use this code to verify your account.
            
            Best Regards,
            The Development Team
            """.formatted(name, otp);

        emailService.sendEmail(to, subject, body);
    }
}
