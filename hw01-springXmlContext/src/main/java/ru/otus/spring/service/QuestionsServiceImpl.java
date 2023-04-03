package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;

@RequiredArgsConstructor
public class QuestionsServiceImpl implements QuestionService {

    private final QuestionDao questionDao;

    private final OutputQuestionAndAnswersService outputQuestionAndAnswersService;

    @Override
    public void printAllQuestions() {

        for (Question question : questionDao.findAll()) {
            outputQuestionAndAnswersService.printQuestion(question);
        }
    }
}
