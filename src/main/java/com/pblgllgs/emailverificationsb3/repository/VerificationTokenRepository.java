package com.pblgllgs.emailverificationsb3.repository;

import com.pblgllgs.emailverificationsb3.token.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
}
