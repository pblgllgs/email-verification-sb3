package com.pblgllgs.emailverificationsb3.security;

import com.pblgllgs.emailverificationsb3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserRegistrationDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findUserByEmail(username).map(
                UserRegistrationDetails::new
        ).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
