package ru.otus.spring.dao;

public class QuestionDaoException extends RuntimeException {
    public QuestionDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}