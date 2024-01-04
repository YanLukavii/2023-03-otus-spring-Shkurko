package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    public Optional<Comment> findById(String id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findByBookId(String bookId) {

        var book = bookRepository.findById(bookId).
                orElseThrow(() -> new EntityNotFoundException("Book with id %s not found".formatted(bookId)));

        return commentRepository.findByBook(book);
    }

    @Override
    public Comment insert(String text, String bookId) {

        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %s not found".formatted(bookId)));

        var comment = new Comment(text, book);

        return commentRepository.save(comment);
    }

    @Override
    public Comment update(String id, String text) {

        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %s not found".formatted(id)));

        comment.setText(text);

        return commentRepository.save(comment);
    }

    @Override
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }

}