package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс QuestionServiceImplTest")
public class QuestionServiceImplTest {

    QuestionDao questionDao = Mockito.spy(QuestionDao.class);

    Question question = new Question("what is the best beer?",
            List.of("baltika", "saldens", "franziskaner"));

    String expectedOutput = """
            Question: what is the best beer?\r
            Answer: baltika\r
            Answer: saldens\r
            Answer: franziskaner\r
            """;


    @DisplayName("корректно выводит вопросы и ответы")
    @Test
    void shouldReturnCorrectOutputWhenCallPrintAllQuestions() {

        Mockito.when(questionDao.findAll()).thenReturn(List.of(question));

        var byteArrayOutputStream = new ByteArrayOutputStream();
        var ioService = new IOServiceImpl(new PrintStream(byteArrayOutputStream));

        var questionsService = new QuestionsServiceImpl(questionDao,
                new OutputQuestionAndAnswersServiceImpl(ioService));

        questionsService.printAllQuestions();

        assertEquals(expectedOutput, String.valueOf(byteArrayOutputStream));

    }
}
