package ru.otus.spring.service;

import lombok.NoArgsConstructor;
import ru.otus.spring.domain.Question;

@NoArgsConstructor
public class OutputQuestionAndAnswersServiceImpl implements OutputQuestionAndAnswersService {
    public void printQuestion(Question question) {
        System.out.println("Question: " + question.getQuestion());

        for (String answer : question.getAnswers()) {
            System.out.println("Answer: " + answer);
        }
    }

}
