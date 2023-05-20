package ru.otus.app.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.app.dao.QuestionDao;
import ru.otus.app.dao.QuestionDaoImpl;
import ru.otus.app.domain.Question;
import ru.otus.app.repository.ResourceProvider;

import java.util.List;

@DisplayName("класс QuestionDaoImpl")
public class QuestionDaoImplTest {

    ResourceProvider resourceProvider = Mockito.spy(ResourceProvider.class);

    List<Question> expectedQuestionList = List.of(new Question("what is the best beer?",
            List.of("baltika", "saldens", "franziskaner"), 1));

    @DisplayName("корректно читает вопросы")
    @Test
    void shouldReturnCorrectListWithQuestions() {

        Mockito.when(resourceProvider.getFilePath()).thenReturn("questions_en.csv");
        QuestionDao questionDao = new QuestionDaoImpl(resourceProvider);

        List<Question> actualQusetionList = questionDao.findAll();

        Assertions.assertIterableEquals(expectedQuestionList, actualQusetionList);
    }
}