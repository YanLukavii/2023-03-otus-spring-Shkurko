package ru.otus.hw.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw.models.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
