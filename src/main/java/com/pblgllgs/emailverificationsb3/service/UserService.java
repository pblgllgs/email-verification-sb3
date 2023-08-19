package com.pblgllgs.emailverificationsb3.service;

import com.pblgllgs.emailverificationsb3.exception.UserAlreadyExistsException;
import com.pblgllgs.emailverificationsb3.model.User;
import com.pblgllgs.emailverificationsb3.model.dto.RegistrationRequest;
import com.pblgllgs.emailverificationsb3.repository.UserRepository;
import com.pblgllgs.emailverificationsb3.repository.VerificationTokenRepository;
import com.pblgllgs.emailverificationsb3.token.VerificationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest request) {
        Optional<User> user = this.findByEmail(request.email());
        if (user.isPresent()) {
            throw new UserAlreadyExistsException("User with email " + request.email() + " already exists...");
        }
        User newUser = new User();
        newUser.setFirstName(request.firstName());
        newUser.setLastName(request.lastName());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRole(request.role());

        return userRepository.save(newUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void saveUserVerificationToken(User user, String verificationToken) {
        var token = new VerificationToken(verificationToken, user);
        verificationTokenRepository.save(token);
    }

    @Override
    public String validateToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            return "Token dont send in the request";
        }
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if (verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return "Token already expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }
}
