package ru.otus.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Author {

    private long id;

    private final String name;

    public Author(String name) {
        this.name = name;
    }
}
