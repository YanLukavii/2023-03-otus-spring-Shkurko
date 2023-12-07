package ru.otus.hw.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw.models.Genre;

public interface GenreRepository extends CrudRepository<Genre, Long> {
}