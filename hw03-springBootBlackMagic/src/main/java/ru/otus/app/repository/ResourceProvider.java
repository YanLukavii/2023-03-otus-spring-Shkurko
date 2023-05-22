package ru.otus.app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.app.configs.providers.LocaleProvider;

@Service
public class ResourceProvider {

    private final LocaleProvider localeProvider;
    private final MessageSource messageSource;

    @Autowired
    public ResourceProvider(LocaleProvider localeProvider, MessageSource messageSource) {
        this.localeProvider = localeProvider;
        this.messageSource = messageSource;
    }

    public String getFilePath() {
        return messageSource.getMessage("file.questions", null, localeProvider.getLocale());
    }
}