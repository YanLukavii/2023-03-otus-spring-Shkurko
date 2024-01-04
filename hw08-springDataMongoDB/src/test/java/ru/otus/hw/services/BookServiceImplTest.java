package ru.otus.hw.services;


import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DisplayName("BookServiceImplTest ")
public class BookServiceImplTest extends AbstractServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private CommentService commentService;

    private final static String EXIST_BOOK_ID = "one";

    private final static String NOT_EXIST_BOOK_ID = "four";

    private final static String EXIST_AUTHOR_ID = "one";

    private final static String NOT_EXIST_AUTHOR_ID = "four";

    private final static String EXIST_GENRE_ID = "one";

    private final static String NOT_EXIST_GENRE_ID = "four";

    private final Genre genre_1 = new Genre("one", "Genre_1");

    private final Author author_1 = new Author("one", "Author_1");

    private final Book expectedBook = new Book("one", "Book_1", author_1, genre_1);


    @DisplayName("должен кидать EntityNotFoundException во время сохранения книги с отсутствующими в БД Author")
    @Test
    void shouldThrowEntityNotFoundExceptionWhenSaveBookWithNotExistAuthor() {

        assertThatThrownBy(() -> bookService.insert("new_book_title", NOT_EXIST_AUTHOR_ID, EXIST_GENRE_ID))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("должен кидать EntityNotFoundException во время сохранения книги с отсутствующими в БД Genre")
    @Test
    void shouldThrowEntityNotFoundExceptionWhenSaveBookWithNotExistGenre() {

        assertThatThrownBy(() -> bookService.insert("new_book_title", EXIST_AUTHOR_ID, NOT_EXIST_GENRE_ID))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("должен кидать EntityNotFoundException во время поиска книги с отсутствующими в БД Book")
    @Test
    void shouldThrowEntityNotFoundExceptionWhenFindBookWithNotExistBook() {

        assertThatThrownBy(() -> bookService.findById(NOT_EXIST_BOOK_ID))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("должен кидать EntityNotFoundException во время изменения книги с отсутствующими в БД Book")
    @Test
    void shouldThrowEntityNotFoundExceptionWhenUpdateBookWithNotExistBook() {

        assertThatThrownBy(() -> bookService.update(NOT_EXIST_BOOK_ID, "new_book_title", EXIST_AUTHOR_ID, EXIST_GENRE_ID))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("должен кидать EntityNotFoundException во время изменения книги с отсутствующими в БД Author")
    @Test
    void shouldThrowEntityNotFoundExceptionWhenUpdateBookWithNotExistAuthor() {

        assertThatThrownBy(() -> bookService.update(EXIST_BOOK_ID, "new_book_title", NOT_EXIST_AUTHOR_ID, EXIST_GENRE_ID))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("должен кидать EntityNotFoundException во время изменения книги с отсутствующими в БД Genre")
    @Test
    void shouldThrowEntityNotFoundExceptionWhenUpdateBookWithNotExistGenre() {

        assertThatThrownBy(() -> bookService.update(EXIST_BOOK_ID, "new_book_title", EXIST_AUTHOR_ID, NOT_EXIST_GENRE_ID))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("должен загружать книгу по id")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldReturnExpectedBookById() {

        var actualBook = bookService.findById(expectedBook.getId());

        assertThat(actualBook)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        var expectedBook = new Book("BookTitle_10500", author_1, genre_1);

        var returnedBook = bookService.insert(expectedBook.getTitle(),
                expectedBook.getAuthor().getId(),
                expectedBook.getGenre().getId());

        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() != null)
                .usingRecursiveComparison().ignoringExpectedNullFields()
                .isEqualTo(expectedBook);

        assertThat(bookService.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(returnedBook);
    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldSaveUpdatedBook() {
        var expectedBook = new Book("one", "BookTitle_10500", author_1, genre_1);

        assertThat(bookService.findById(expectedBook.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedBook);

        var returnedBook = bookService.update(expectedBook.getId(),
                expectedBook.getTitle(),
                expectedBook.getAuthor().getId(),
                expectedBook.getGenre().getId());

        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() != null)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(bookService.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(returnedBook);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("при удалении Book должен удалять его Comment из БД")
    @Test
    void shouldDeleteCommentsWhenRemovingBook() {

        // Загрузка комментариев по книге
        val comments = commentService.findByBookId(expectedBook.getId());
        val comment = comments.get(0);
        val commentId = comment.getId();

        //Проверка наличия комментария в БД
        assertThat(commentService.findById(commentId))
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(comment);

        // Удаление книги
        bookService.deleteById(expectedBook.getId());

        //Проверка отсутствия комментария в БД после удаления книги
        assertThatThrownBy(() -> commentService.findByBookId(commentId))
                .isInstanceOf(EntityNotFoundException.class);

    }
}