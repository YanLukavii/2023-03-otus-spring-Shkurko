package ru.otus.spring.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@RequiredArgsConstructor
public class Question {

    private final String question;

    private final List<String> answers;

}
