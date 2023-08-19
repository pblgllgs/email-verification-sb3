package com.pblgllgs.emailverificationsb3.exception;

import java.time.LocalDateTime;

public record ApiError(
        String path,
        String message,
        int status,
        LocalDateTime localDateTime) {
}
