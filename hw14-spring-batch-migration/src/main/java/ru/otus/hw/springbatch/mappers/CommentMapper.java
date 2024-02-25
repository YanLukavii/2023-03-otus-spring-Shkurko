package ru.otus.hw.springbatch.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.otus.hw.springbatch.dto.CommentDto;
import ru.otus.hw.springbatch.model.h2.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mappings({
            @Mapping(target = "bookId", source = "book.id"),
    })
    CommentDto toDto(Comment comment);

}