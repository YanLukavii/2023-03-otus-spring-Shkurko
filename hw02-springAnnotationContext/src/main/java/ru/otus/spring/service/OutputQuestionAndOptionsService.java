package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

public interface OutputQuestionAndOptionsService {
    void printQuestionAndOptions(Question question);
}