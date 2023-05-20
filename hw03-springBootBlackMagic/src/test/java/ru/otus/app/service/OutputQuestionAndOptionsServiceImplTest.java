package ru.otus.app.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.app.domain.Question;
import ru.otus.app.repository.LocalizationAppPropertiesProvider;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс OutputQuestionAndOptionsServiceImpl")
public class OutputQuestionAndOptionsServiceImplTest {
    LocalizationAppPropertiesProvider localizationAppPropertiesProvider
            = Mockito.mock(LocalizationAppPropertiesProvider.class);

    Question question = new Question("what is the best beer?",
            List.of("baltika", "saldens", "franziskaner"), 1);

    String expectedOutput ="Question:what is the best beer?\r\n1) baltika\r\n2) saldens\r\n3) franziskaner\r\n";

    @DisplayName("корректно выводит вопросы и ответы")
    @Test
    void shouldReturnCorrectOutputWhenCallQuestionAndOptions() {

        Mockito.when(localizationAppPropertiesProvider
                        .getPropertyValue("output.question"))
                .thenReturn("Question:");
        var byteArrayOutputStream = new ByteArrayOutputStream();
        var ioService = new IOServiceImpl(new PrintStream(byteArrayOutputStream), System.in);

        var outputQuestionAndOptionsService = new OutputQuestionAndOptionsServiceImpl(ioService,
                localizationAppPropertiesProvider);

        outputQuestionAndOptionsService.printQuestionAndOptions(question);

        assertEquals(expectedOutput, String.valueOf(byteArrayOutputStream));

    }
}