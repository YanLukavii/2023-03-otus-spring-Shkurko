package ru.otus.hw.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Book;


@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mappings({
            @Mapping(target = "authorId", source = "author.id"),
            @Mapping(target = "genreId", source = "genre.id")
    })
    BookDto toDto(Book book);

}