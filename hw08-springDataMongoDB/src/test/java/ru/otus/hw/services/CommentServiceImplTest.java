package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("CommentServiceImplTest ")
public class CommentServiceImplTest extends AbstractServiceTest {

    @Autowired
    private CommentService commentService;

    private final static String NOT_EXIST_BOOK_ID = "four";

    private final static String NOT_EXIST_COMMENT_ID = "four";

    private final Genre genre_1 = new Genre("one", "Genre_1");

    private final Author author_1 = new Author("one", "Author_1");

    private final Book book_1 = new Book("one", "Book_1", author_1, genre_1);

    private final Comment expectedComment = new Comment("one", "Comment_1", book_1);

    @DisplayName("должен кидать EntityNotFoundException во время сохранения комментария с отсутствующей в БД Book")
    @Test
    void shouldThrowEntityNotFoundExceptionWhenSaveCommentWithNotExistBook() {

        assertThatThrownBy(() -> commentService.insert("new_text_comment", NOT_EXIST_BOOK_ID))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("должен кидать EntityNotFoundException во время поиска комментария с отсутствующей в БД Book")
    @Test
    void shouldThrowEntityNotFoundExceptionWhenFindCommentWithNotExistBook() {

        assertThatThrownBy(() -> commentService.findByBookId(NOT_EXIST_BOOK_ID))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("должен кидать EntityNotFoundException во время изменения комментария с отсутствующей в БД Comment")
    @Test
    void shouldThrowEntityNotFoundExceptionWhenUpdateCommentWithNotExistComment() {

        assertThatThrownBy(() -> commentService.update(NOT_EXIST_COMMENT_ID, "new_text_comment"))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("должен загружать комментарий по id")
    @Test
    void shouldReturnExpectedCommentById() {

        var actualComment = commentService.findById(expectedComment.getId());

        assertThat(actualComment)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedComment);
    }

    @DisplayName("должен загружать список всех комментариев по книге")
    @Test
    void shouldReturnCorrectCommentsListByBook() {
        var actualComments = commentService.findByBookId(book_1.getId());
        var expectedComments = List.of(expectedComment);

        assertThat(actualComments)
                .usingRecursiveComparison()
                .ignoringFields("book")
                .isEqualTo(expectedComments);
    }

    @DisplayName("должен сохранять новый комментарий")
    @Test
    void shouldSaveNewComment() {

        var expectedComment = new Comment("CommentTitle_10500", book_1);
        var returnedComment = commentService.insert(expectedComment.getText()
                , expectedComment.getBook().getId());

        assertThat(returnedComment).isNotNull()
                .matches(comment -> comment.getId() != null)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedComment);

        assertThat(commentService.findById(returnedComment.getId()))
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

        assertThat(commentService.findById(expectedComment.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedComment);

        var returnedComment = commentService.update(expectedComment.getId(), expectedComment.getText());
        assertThat(returnedComment).isNotNull()
                .matches(comment -> comment.getId() != null)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedComment);

        assertThat(commentService.findById(returnedComment.getId()))
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(returnedComment);
    }

}