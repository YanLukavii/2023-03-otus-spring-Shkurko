package ru.otus.app.dao;

import ru.otus.app.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findAll();

}
