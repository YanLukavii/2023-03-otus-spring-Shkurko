package ru.otus.app.service;

import ru.otus.app.domain.Question;

public interface OutputQuestionAndOptionsService {
    void printQuestionAndOptions(Question question);
}