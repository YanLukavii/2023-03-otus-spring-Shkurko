package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.List;


public interface CommentRepository  extends JpaRepository< Comment, Long> {
    List<Comment> findByBook(Book book);
}