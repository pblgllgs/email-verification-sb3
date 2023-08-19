package com.pblgllgs.emailverificationsb3.service;

import com.pblgllgs.emailverificationsb3.model.User;
import com.pblgllgs.emailverificationsb3.model.dto.RegistrationRequest;
import com.pblgllgs.emailverificationsb3.token.VerificationToken;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getUsers();
    User registerUser(RegistrationRequest request);
    Optional<User> findByEmail(String email);

    void saveUserVerificationToken(User user, String verificationToken);

    String validateToken(String token);
}
