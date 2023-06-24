package ru.otus.app.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

    private final String name;

    private final String surname;

    private int score;

    public Student(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}