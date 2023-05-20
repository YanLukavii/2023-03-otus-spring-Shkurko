package ru.otus.app.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@Data
@ConfigurationProperties(prefix = "application")
public class AppProps {

    private final int points;

    private final Locale locale;

}
