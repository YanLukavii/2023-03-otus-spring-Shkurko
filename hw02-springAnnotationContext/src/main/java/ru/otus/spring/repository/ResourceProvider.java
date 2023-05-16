package ru.otus.spring.repository;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
@Data
@NoArgsConstructor
public class ResourceProvider {

    private String filePath;

    @Autowired
    public ResourceProvider(@Value("${file.questions}") String filePath) {
        this.filePath = filePath;
    }
}