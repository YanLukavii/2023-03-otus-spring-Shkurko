package ru.otus.app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.app.configs.providers.LocaleProvider;

@Service
public class ResourceProvider {

    private final String filePath;

    @Autowired
    public ResourceProvider(LocaleProvider localeProvider, MessageSource messageSource) {
        this.filePath = messageSource.getMessage("file.questions", null, localeProvider.getLocale());
    }

    public String getFilePath() {
        return filePath;
    }
}