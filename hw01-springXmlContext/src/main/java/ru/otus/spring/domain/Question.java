package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

@ToString
@Getter
@AllArgsConstructor
public class Question {

    private final String question;

    private final List<String> answers;

}
