package ru.otus.hw.mappers;

import org.mapstruct.Mapper;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.models.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDto toDto(Author author);

}