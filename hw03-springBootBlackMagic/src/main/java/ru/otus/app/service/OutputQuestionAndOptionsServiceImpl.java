package ru.otus.app.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.otus.app.domain.Question;
import ru.otus.app.configs.providers.LocalizationSettingsProvider;

@Service
@Data
public class OutputQuestionAndOptionsServiceImpl implements OutputQuestionAndOptionsService {

    private final IOService ioService;

    private final LocalizationSettingsProvider localizationSettingsProvider;

    public OutputQuestionAndOptionsServiceImpl(IOService ioService,
                                               LocalizationSettingsProvider localizationSettingsProvider) {
        this.ioService = ioService;
        this.localizationSettingsProvider = localizationSettingsProvider;
    }

    public void printQuestionAndOptions(Question question) {

        ioService.print(localizationSettingsProvider.getOutputQuestionMessage()
                + question.getQuestion());

        int answerOption = 0;
        for (String answer : question.getAnswerOptions()) {
            ioService.print(++answerOption + ") " + answer);
        }
    }
}