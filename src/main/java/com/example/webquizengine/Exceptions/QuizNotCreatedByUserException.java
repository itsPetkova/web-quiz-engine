package com.example.webquizengine.Exceptions;

public class QuizNotCreatedByUserException extends RuntimeException {
    public QuizNotCreatedByUserException(String message) {
        super(message);
    }

    public QuizNotCreatedByUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
