package ru.otus.spring.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.spring.domain.Question;
import ru.otus.spring.repository.ResourceProvider;

import java.util.List;

@DisplayName("класс QuestionDaoImpl")
public class QuestionDaoImplTest {

    ResourceProvider resourceProvider = Mockito.spy(ResourceProvider.class);

    List<Question> expectedQuestionList = List.of(new Question("what is the best beer?",
            List.of("baltika", "saldens", "franziskaner"), 1));

    @DisplayName("корректно читает вопросы")
    @Test
    void shouldReturnCorrectListWithQuestions() {

        Mockito.when(resourceProvider.getFilePath()).thenReturn("questions.csv");
        QuestionDao questionDao = new QuestionDaoImpl(resourceProvider);

        List<Question> actualQusetionList = questionDao.findAll();

        Assertions.assertIterableEquals(expectedQuestionList, actualQusetionList);
    }
}