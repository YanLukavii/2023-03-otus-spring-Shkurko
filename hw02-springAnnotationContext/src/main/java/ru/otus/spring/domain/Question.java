package ru.otus.spring.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class Question {

    private final String question;

    private final List<String> answerOptions;

    private final int rightOption;

}