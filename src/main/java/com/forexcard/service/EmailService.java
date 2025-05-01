package com.forexcard.service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.forexcard.model.User;
import com.forexcard.repo.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    private final Map<Integer, String> otpCache = new ConcurrentHashMap<>();
    private final SecureRandom secureRandom = new SecureRandom();

    // ===== Simplified HTML Email Template =====
    private String buildEmailTemplate(String messageBody, String name) {
        return """
            <html>
                <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                    <div style="max-width: 600px; margin: auto; padding: 20px; border-radius: 10px; border: 1px solid #ddd; box-shadow: 0 4px 8px rgba(0,0,0,0.05);">
                        <p>Dear %s,</p>
                        <p>%s</p>

                        <p>If you have any questions or need help, feel free to reach out to our support team at 
                        <a href="mailto:support@forexcard.com" style="color: #007BFF;">support@forexcard.com</a>. 
                        We’re always here to help!</p>

                        <p>You can also log in to your dashboard to check the latest updates or manage your account: 
                        <a href="https://forexcard.com/dashboard" style="color: #007BFF;">Go to Dashboard</a></p>

                        <br/>
                        <p>Thank you for choosing ForexCard – your trusted partner for seamless international transactions.</p>

                        <p>Best regards,<br/>
                        <strong>ForexCard Team</strong><br/>
                        <a href="https://forexcard.com" style="color: #007BFF;">www.forexcard.com</a></p>

                        <hr style="margin-top: 30px;" />
                        <p style="font-size: 12px; color: #888;">This is an automated message. Please do not reply.</p>
                    </div>
                </body>
            </html>
        """.formatted(name, messageBody);
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // Enable HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("Email send failed: " + e.getMessage());
        }
    }

    @Async
    public void sendRegistrationSuccessEmail(String to) {
        Optional<User> userOpt = userRepository.findByEmail(to);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String subject = "Welcome to ForexCard!";
            String body = "Thank you for registering with ForexCard. We’re thrilled to have you on board. Explore our platform and enjoy seamless forex services.";
            sendHtmlEmail(to, subject, buildEmailTemplate(body, user.getName()));
        }
    }

    @Async
    public void sendCardApplicationConfirmation(String to) {
        Optional<User> userOpt = userRepository.findByEmail(to);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String subject = "ForexCard Application Received";
            String body = "We have received your ForexCard application. Our team is reviewing your request, and you can expect a response within 2-5 business days.";
            sendHtmlEmail(to, subject, buildEmailTemplate(body, user.getName()));
        }
    }

    @Async
    public void sendCardActivationConfirmation(String to) {
        Optional<User> userOpt = userRepository.findByEmail(to);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String subject = "ForexCard Activated Successfully";
            String body = "Your ForexCard has been successfully activated. You can now start using it for your international transactions.";
            sendHtmlEmail(to, subject, buildEmailTemplate(body, user.getName()));
        }
    }

    @Async
    public void sendCardApprovalConfirmation(String to) {
        Optional<User> userOpt = userRepository.findByEmail(to);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String subject = "Your ForexCard Has Been Approved";
            String body = "Congratulations! Your ForexCard has been approved. Please activate it to begin using the card’s features and benefits.";
            sendHtmlEmail(to, subject, buildEmailTemplate(body, user.getName()));
        }
    }

    @Async
    public void sendCardRejectionEmail(String to) {
        Optional<User> userOpt = userRepository.findByEmail(to);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String subject = "ForexCard Application Status: Rejected";
            String body = """
                We regret to inform you that your recent ForexCard application has not been approved at this time. 
                This decision was based on our internal review and eligibility criteria.

                If you believe this was a mistake or would like to reapply in the future, 
                please ensure your documents are valid and meet our application standards.

                For any questions or clarifications, you can contact our support team.
            """;
            sendHtmlEmail(to, subject, buildEmailTemplate(body, user.getName()));
        }
    }

    @Async
    public void sendForgotPasswordOtp(Integer userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String otp = generateOtp();

            String subject = "Your OTP for Password Reset";
            String body = "Please use the following One-Time Password (OTP) to reset your password:<br/><br/><strong style='font-size: 18px;'>" + otp + "</strong><br/><br/>This OTP is valid for a limited time only.";
            sendHtmlEmail(user.getEmail(), subject, buildEmailTemplate(body, user.getName()));

            otpCache.put(userId, otp);
        }
    }

    @Async
    public void sendPasswordResetConfirmation(String to) {
        Optional<User> userOpt = userRepository.findByEmail(to);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String subject = "Your Password Has Been Reset";
            String body = "Your password has been successfully reset. If you did not request this change, please contact support immediately.";
            sendHtmlEmail(to, subject, buildEmailTemplate(body, user.getName()));
        }
    }

    @Async
    public void sendOtp(Integer userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String otp = generateOtp();

            String subject = "Your OTP for ForexCard Activation";
            String body = "Please use the following One-Time Password (OTP) to activate your ForexCard:<br/><br/><strong style='font-size: 18px;'>" + otp + "</strong><br/><br/>This OTP is valid for a limited time only.";
            sendHtmlEmail(user.getEmail(), subject, buildEmailTemplate(body, user.getName()));

            otpCache.put(userId, otp);
        }
    }

    public String getStoredOtp(Integer userId) {
        return otpCache.get(userId);
    }

    public void clearOtp(Integer userId) {
        otpCache.remove(userId);
    }

    private String generateOtp() {
        int otp = secureRandom.nextInt(9000) + 1000;
        return String.valueOf(otp);
    }

    @Async
    public void sendTransactionReport(String to, byte[] pdfData, String fileName) {
        Optional<User> userOpt = userRepository.findByEmail(to);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

                helper.setTo(to);
                helper.setSubject("Your ForexCard Transaction Report");

                String body = "Attached is the PDF copy of your recent ForexCard transaction report. Please contact support if you have any questions.";
                helper.setText(buildEmailTemplate(body, user.getName()), true);
                helper.addAttachment(fileName, new ByteArrayDataSource(pdfData, "application/pdf"));

                mailSender.send(message);
            } catch (MessagingException e) {
                System.err.println("Failed to send transaction report: " + e.getMessage());
            }
        }
    }

    public String getStoredOtpByEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return otpCache.get(user.getId());
        }
        return null;
    }

    public void clearOtpByEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            otpCache.remove(user.getId());
        }
    }
}
