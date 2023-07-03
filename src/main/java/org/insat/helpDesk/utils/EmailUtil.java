package org.insat.helpDesk.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Verify OTP");
        mimeMessageHelper.setText("""
            <div style="color: black">
                <h1>Welcome to G_tickets</h1>
                <h4>Please click the link below to verify your email address.</h4> <br>
            <a href="http://localhost:4200/verify-account?email=%s&otp=%s" target="_blank" 
            style="background-color: black; color: white; border-radius: 10px; padding: 10px; text-decoration: none;">
            Click link to verify</a>
            <h4>The link is valid for 1 minute.</h4>
            </div>
            """.formatted(email, otp), true)
;

        javaMailSender.send(mimeMessage);
    }
}
