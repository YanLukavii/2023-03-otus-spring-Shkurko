package ru.otus.hw.springbatch.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.otus.hw.springbatch.dto.BookDto;
import ru.otus.hw.springbatch.model.h2.Book;


@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mappings({
            @Mapping(target = "authorId", source = "author.id"),
            @Mapping(target = "genreId", source = "genre.id")
    })
    BookDto toDto(Book book);

}