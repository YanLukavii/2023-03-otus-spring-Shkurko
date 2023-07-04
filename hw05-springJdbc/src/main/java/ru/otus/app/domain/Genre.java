package ru.otus.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre {

    private Long id;

    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
