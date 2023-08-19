package com.pblgllgs.emailverificationsb3.token;

import com.pblgllgs.emailverificationsb3.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@Entity(name = "VerificationToken")
@Table(name = "verification_tokens")
@NoArgsConstructor
public class VerificationToken {

    private static final int EXPIRATION_TIME = 15;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expirationTime;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    public VerificationToken(String token, User user) {
        this.user = user;
        this.token = token;
        this.expirationTime = this.getTokenExpirationTime();
    }

    public VerificationToken(String token) {
        this.token = token;
        this.expirationTime = this.getTokenExpirationTime();
    }

    private Date getTokenExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }
}
