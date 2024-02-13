package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.mappers.CommentMapper;
import ru.otus.hw.repositories.CommentRepository;

@RestController
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    @GetMapping("/api/comments/{id}")
    public Flux<CommentDto> getCommentsByBookId(@PathVariable("id") String id) {
        return commentRepository
                .findByBookId(id)
                .map(commentMapper::toDto);
    }

}