package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;
import java.util.List;

@AllArgsConstructor
public class QuestionsServiceImpl implements QuestionService {

    private final QuestionDao questionDao;

    @Override
    public List<Question> getAll() {
        return questionDao.findAll();
    }
}
