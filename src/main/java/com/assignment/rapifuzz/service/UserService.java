package com.assignment.rapifuzz.service;

import com.assignment.rapifuzz.dto.response.ForgetPasswordResponseDTO;
import com.assignment.rapifuzz.dto.response.LoginResponseDTO;
import com.assignment.rapifuzz.entity.User;
import com.assignment.rapifuzz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    @Autowired
    public UserService(UserRepository userRepository,JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }


    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        String hashedPassword = BCrypt.hashpw(user.getPasswordHash(), BCrypt.gensalt());
        user.setPasswordHash(hashedPassword);
        return userRepository.save(user);
    }

    public LoginResponseDTO loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (BCrypt.checkpw(password, user.getPasswordHash())) {
            return LoginResponseDTO.builder()
                    .message("Login successful")
                    .statusCode("200")
                    .build();
        } else {
            throw new RuntimeException("Invalid email or password");
        }
    }

    public ForgetPasswordResponseDTO processForgotPassword(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("Email not found: " + email);
        }
        String resetToken = UUID.randomUUID().toString();
        String resetLink = "http://localhost:8080/api/users/reset-password?token=" + resetToken;
        sendEmail(email, "Password Reset Request",
                "Hi " + userOptional.get().getEmail() + ",\n\n" +
                        "To reset your password, click the link below:\n" +
                        resetLink + "\n\n" +
                        "If you did not request this, please ignore this email.\n\n" +
                        "Thanks,\nYour Team");
        return ForgetPasswordResponseDTO.builder()
                .message("Password reset link sent to: " + email)
                .build();
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
