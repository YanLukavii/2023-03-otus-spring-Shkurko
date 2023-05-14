package ru.otus.spring.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;

@Service
@Data
public class OutputQuestionAndOptionsServiceImpl implements OutputQuestionAndOptionsService {

    private final IOService ioService;

    @Autowired
    public OutputQuestionAndOptionsServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    public void printQuestionAndOptions(Question question) {

        ioService.print("Question: " + question.getQuestion());

        int answerOption = 0;
        for (String answer : question.getAnswerOptions()) {
            ioService.print(++answerOption + ") " + answer);
        }
    }

}