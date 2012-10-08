package com.ngdb.web.services.infrastructure;

public class UnavailableRatingException extends RuntimeException {
    public UnavailableRatingException(String message) {
        super(message);
    }
}
