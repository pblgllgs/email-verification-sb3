package com.pblgllgs.emailverificationsb3.model.dto;

public record RegistrationRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        String role
) {
}
