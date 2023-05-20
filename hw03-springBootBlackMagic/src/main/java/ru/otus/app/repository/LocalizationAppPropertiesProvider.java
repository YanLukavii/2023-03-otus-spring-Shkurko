package ru.otus.app.repository;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.app.configs.AppProps;

@Component
@Data
@NoArgsConstructor
public class LocalizationAppPropertiesProvider {

    private MessageSource messageSource;

    private AppProps props;

    public LocalizationAppPropertiesProvider(MessageSource messageSource, AppProps props) {
        this.messageSource = messageSource;
        this.props = props;
    }

    public String getPropertyValue(String code) {
        return messageSource.getMessage(code,null,props.getLocale());
    }
}
