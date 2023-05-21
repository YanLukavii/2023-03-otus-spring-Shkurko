package ru.otus.app.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.app.configs.providers.QuestionsPathProvider;
import ru.otus.app.domain.Question;

import java.util.List;

@DisplayName("класс QuestionDaoImpl")
public class QuestionDaoImplTest {

    QuestionsPathProvider questionsPathProvider = Mockito.mock(QuestionsPathProvider.class);

    List<Question> expectedQuestionList = List.of(new Question("what is the best beer?",
            List.of("baltika", "saldens", "franziskaner"), 1));

    @DisplayName("корректно читает вопросы")
    @Test
    void shouldReturnCorrectListWithQuestions() {

        Mockito.when(questionsPathProvider.getQuestionsPath()).thenReturn("questions_en.csv");
        QuestionDao questionDao = new QuestionDaoImpl(questionsPathProvider);

        List<Question> actualQusetionList = questionDao.findAll();

        Assertions.assertIterableEquals(expectedQuestionList, actualQusetionList);
    }
}
