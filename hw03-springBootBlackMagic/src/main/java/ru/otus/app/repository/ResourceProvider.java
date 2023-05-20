package ru.otus.app.repository;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Data
@NoArgsConstructor
public class ResourceProvider {

    private  LocalizationAppPropertiesProvider localizationAppPropertiesProvider;

    private  String filePath;

    public ResourceProvider(LocalizationAppPropertiesProvider localizationAppPropertiesProvider) {
        this.localizationAppPropertiesProvider = localizationAppPropertiesProvider;
        this.filePath = localizationAppPropertiesProvider.getPropertyValue("file.questions");
    }
}