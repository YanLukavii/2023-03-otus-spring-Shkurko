package ru.otus.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Author {

    private Long id;

    private String name;

    public Author(String name) {
        this.name = name;
    }
}
