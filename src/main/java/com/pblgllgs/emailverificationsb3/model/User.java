package com.pblgllgs.emailverificationsb3.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(name = "email_UK", columnNames = "email")})
@Entity(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(
            name = "first_name",
            nullable = false
    )
    private String firstName;
    @Column(
            name = "lasat_name",
            nullable = false
    )
    private String lastName;
    @NaturalId(mutable = true)
    private String email;
    private String password;
    private String role;
    private boolean isEnabled = false;
}
