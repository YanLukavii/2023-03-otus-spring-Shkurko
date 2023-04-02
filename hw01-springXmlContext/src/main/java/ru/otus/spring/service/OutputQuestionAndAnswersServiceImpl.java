package ru.otus.spring.service;

import lombok.Data;
import ru.otus.spring.domain.Question;

@Data
public class OutputQuestionAndAnswersServiceImpl implements OutputQuestionAndAnswersService {

    private final IOService ioService;

    public void printQuestion(Question question) {
        ioService.print("Question: " + question.getQuestion());

        for (String answer : question.getAnswers()) {
            ioService.print("Answer: " + answer);
        }
    }

}
