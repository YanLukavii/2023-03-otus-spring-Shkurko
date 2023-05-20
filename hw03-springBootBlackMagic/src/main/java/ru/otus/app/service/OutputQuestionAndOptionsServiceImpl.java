package ru.otus.app.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.otus.app.domain.Question;
import ru.otus.app.repository.LocalizationAppPropertiesProvider;

@Service
@Data
public class OutputQuestionAndOptionsServiceImpl implements OutputQuestionAndOptionsService {

    private final IOService ioService;

    private final LocalizationAppPropertiesProvider localizationAppPropertiesProvider;

    public OutputQuestionAndOptionsServiceImpl(IOService ioService,
                                               LocalizationAppPropertiesProvider localizationAppPropertiesProvider) {
        this.ioService = ioService;
        this.localizationAppPropertiesProvider = localizationAppPropertiesProvider;
    }

    public void printQuestionAndOptions(Question question) {

        ioService.print(localizationAppPropertiesProvider.getPropertyValue("output.question")
                + question.getQuestion());

        int answerOption = 0;
        for (String answer : question.getAnswerOptions()) {
            ioService.print(++answerOption + ") " + answer);
        }
    }
}