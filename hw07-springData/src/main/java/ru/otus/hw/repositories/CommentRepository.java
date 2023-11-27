package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;


public interface CommentRepository  extends JpaRepository< Comment, Long> {

    Optional<Comment> findById(long id);

    List<Comment> findByBook(Book book);

    Comment save(Comment comment);

    void deleteById(long id);

 /*   Comment update(Comment comment);*/
}
