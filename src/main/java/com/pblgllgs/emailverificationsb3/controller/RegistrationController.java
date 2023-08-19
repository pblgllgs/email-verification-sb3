package com.pblgllgs.emailverificationsb3.controller;

import com.pblgllgs.emailverificationsb3.event.RegistrationCompleteEvent;
import com.pblgllgs.emailverificationsb3.model.User;
import com.pblgllgs.emailverificationsb3.model.dto.RegistrationRequest;
import com.pblgllgs.emailverificationsb3.repository.VerificationTokenRepository;
import com.pblgllgs.emailverificationsb3.service.UserService;
import com.pblgllgs.emailverificationsb3.token.VerificationToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository verificationTokenRepository;
    @PostMapping
    public String registerUser(@RequestBody RegistrationRequest registrationRequest, final HttpServletRequest request) {
        User user = userService.registerUser(registrationRequest);
        log.info(user.toString());
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return "Success, Please check your email to complete your registration";
    }

    @GetMapping("/verifyYourEmail")
    public String verifyEmail(@RequestParam("token") String token){
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken.getUser().isEnabled()){
            return "this account has already verified, please login with your credentials";
        }
        String verificationResult = userService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")){
            return "Email verified successfully";
        }
        return "invalid verification token";
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
