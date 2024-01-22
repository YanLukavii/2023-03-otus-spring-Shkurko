package ru.otus.hw.services;

import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentUpdateDto;
import java.util.List;

public interface CommentService {

    CommentDto findById (long id);

    List<CommentDto> findByBookId (long bookId);

    CommentDto create(CommentCreateDto dto);

    CommentDto update(CommentUpdateDto dto);

    void deleteById(long id);

}
