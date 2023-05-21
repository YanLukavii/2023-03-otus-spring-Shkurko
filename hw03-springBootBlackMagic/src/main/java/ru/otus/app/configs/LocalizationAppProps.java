package ru.otus.app.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.app.configs.providers.LocaleProvider;
import ru.otus.app.configs.providers.LocalizationSettingsProvider;
import ru.otus.app.configs.providers.QuestionsPathProvider;

@Component
@Getter
@Setter
public class LocalizationAppProps implements LocalizationSettingsProvider, QuestionsPathProvider {

    private final MessageSource messageSource;

    private final LocaleProvider localeProvider;

    public LocalizationAppProps(MessageSource messageSource, LocaleProvider localeProvider) {
        this.messageSource = messageSource;
        this.localeProvider = localeProvider;
    }

    public String getPropertyValue(String code) {
        return messageSource.getMessage(code, null, localeProvider.getLocale());
    }

    @Override
    public String getStudentNameGreetingMessage() {
        return getPropertyValue("student.name");
    }

    @Override
    public String getStudentSurnameGreetingMessage() {
        return getPropertyValue("student.surname");
    }

    @Override
    public String getOutputQuestionMessage() {
        return getPropertyValue("output.question");
    }

    @Override
    public String getTestPassMessage() {
        return getPropertyValue("test.passed");
    }

    @Override
    public String getTestFailedMessage() {
        return getPropertyValue("test.failed");
    }

    @Override
    public String getQuestionsPath() {
        return getPropertyValue("file.questions");
    }
}
