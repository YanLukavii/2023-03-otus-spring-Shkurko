package ru.otus.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Question")
class QuestionTest {
    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        Question question = new Question("what is the best beer?", List.of("baltika", "saldens", "franziskaner"),3);
        assertEquals("what is the best beer?", question.getQuestion());
        assertEquals(List.of("baltika", "saldens", "franziskaner"), question.getAnswerOptions());
        assertEquals(3, question.getRightOption());
    }
}