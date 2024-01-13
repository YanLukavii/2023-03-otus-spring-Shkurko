package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CommentRepositoryTest ")
public class CommentRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    private final Genre genre_1 = new Genre("one", "Genre_1");

    private final Author author_1 = new Author("one", "Author_1");

    private final Book book_1 = new Book("one", "Book_1", author_1, genre_1);

    private final Comment expectedComment = new Comment("one", "Comment_1", book_1);

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("должен загружать комментарий по id")
    @Test
    void shouldReturnExpectedCommentById() {

        var actualComment = commentRepository.findById(expectedComment.getId());

        assertThat(actualComment)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedComment);
    }

    @DisplayName("должен сохранять новый комментарий")
    @Test
    void shouldSaveNewComment() {

        var expectedComment = new Comment("CommentTitle_10500", book_1);
        var returnedComment = commentRepository.save(expectedComment);

        assertThat(returnedComment).isNotNull()
                .matches(comment -> comment.getId() != null)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedComment);

        assertThat(commentRepository.findById(returnedComment.getId()))
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(returnedComment);
    }

    @DisplayName("должен сохранять измененный комментарий")
    @Test
    void shouldSaveUpdatedComment() {
        var expectedComment = new Comment("one", "CommentTitle_10500", book_1);

        assertThat(commentRepository.findById(expectedComment.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedComment);

        var returnedComment = commentRepository.save(expectedComment);
        assertThat(returnedComment).isNotNull()
                .matches(comment -> comment.getId() != null)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedComment);

        assertThat(commentRepository.findById(returnedComment.getId()))
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(returnedComment);
    }

}