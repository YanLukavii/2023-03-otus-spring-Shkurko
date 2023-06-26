package ru.otus.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Genre {

    private long id;

    private final String name;

    public Genre(String name) {
        this.name = name;
    }
}
