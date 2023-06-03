package ru.otus.app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.app.configs.providers.LocaleProvider;

@Component
public class LocalizationAppPropertiesProvider {

    private final MessageSource messageSource;

    private final LocaleProvider localeProvider;

    @Autowired
    public LocalizationAppPropertiesProvider(MessageSource messageSource,
                                             LocaleProvider localeProvider) {
        this.messageSource = messageSource;
        this.localeProvider = localeProvider;
    }

    public String getPropertyValue(String code) {
        return messageSource.getMessage(code, null, localeProvider.getLocale());
    }
}