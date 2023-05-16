package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.Question;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс OutputQuestionAndOptionsServiceImpl")
public class OutputQuestionAndOptionsServiceImplTest {

    Question question = new Question("what is the best beer?",
            List.of("baltika", "saldens", "franziskaner"), 1);

    String expectedOutput = """
            Question: what is the best beer?\r
            1) baltika\r
            2) saldens\r
            3) franziskaner\r
            """;

    @DisplayName("корректно выводит вопросы и ответы")
    @Test
    void shouldReturnCorrectOutputWhenCallQuestionAndOptions() {

        var byteArrayOutputStream = new ByteArrayOutputStream();
        var ioService = new IOServiceImpl(new PrintStream(byteArrayOutputStream), System.in);

        var outputQuestionAndOptionsService = new OutputQuestionAndOptionsServiceImpl(ioService);
        outputQuestionAndOptionsService.printQuestionAndOptions(question);

        assertEquals(expectedOutput, String.valueOf(byteArrayOutputStream));

    }
}