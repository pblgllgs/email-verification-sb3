package com.pblgllgs.emailverificationsb3.event.listener;

import com.pblgllgs.emailverificationsb3.event.RegistrationCompleteEvent;
import com.pblgllgs.emailverificationsb3.model.User;
import com.pblgllgs.emailverificationsb3.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final JavaMailSender javaMailSender;

    private final UserService userService;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        User user = event.getUser();
        String verificationToken = UUID.randomUUID().toString();
        userService.saveUserVerificationToken(user, verificationToken);
        String url = event.getApplicationUrl() + "/register/verifyYourEmail?token=" + verificationToken;
        try {
            sendVerificationEmail(url,user);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Click the link to verify your registration: {}", url);
    }

    public void sendVerificationEmail(String url, User user) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email verification";
        String senderName = "User Registration Portal Service";
        String mailContent = "<p> Hi, "+ user.getFirstName() +", </p>"+
                "<p>Thank you for registering with us, " +
                "Please, follow the link bellow to complete your registration.</p>"+
                "<a href=\""+url+"\">Verify your email to activate your account</a>"+
                "<p> Thank you <br> Users Registration Portal Service";
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(from,senderName);
        mimeMessageHelper.setTo(user.getEmail());
//        mimeMessageHelper.setCc(emailDto.getCc());
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(mailContent,true);
        javaMailSender.send(mimeMessage);
    }
}
