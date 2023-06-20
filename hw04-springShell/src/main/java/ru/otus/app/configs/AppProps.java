package ru.otus.app.configs;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.otus.app.configs.providers.LocaleProvider;
import ru.otus.app.configs.providers.SettingsProvider;

import java.util.Locale;


@Setter
@ConfigurationProperties(prefix = "application")
public class AppProps implements LocaleProvider, SettingsProvider {

    private final Locale locale;

    private final int points;


    public AppProps(Locale locale, int points) {
        this.locale = locale;
        this.points = points;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public int getPoints() {
        return points;
    }
}
