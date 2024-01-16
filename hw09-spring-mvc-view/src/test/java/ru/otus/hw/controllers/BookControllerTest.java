package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private BookMapper bookMapper;

    private final static List<Book> BOOKS_LIST = List.of(new Book(1, "b_1", new Author(1, "a_1"),
                    new Genre(1, "g_1")),
            new Book(2, "b_2", new Author(2, "a_2"), new Genre(2, "g_2")));

    @Test
    void shouldCallDeleteMethod() throws Exception {
        long id = 1L;
        mvc.perform(post("/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        verify(bookService).deleteById(id);
    }

    @Test
    void shouldRenderCorrectBookList() throws Exception {

        given(bookService.findAll()).willReturn(BOOKS_LIST);

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("b_1")))
                .andExpect(content().string(containsString("a_1")))
                .andExpect(content().string(containsString("g_1")))
                .andExpect(content().string(containsString("b_2")))
                .andExpect(content().string(containsString("a_2")))
                .andExpect(content().string(containsString("g_2")));

    }

    @Test
    public void testListPage() throws Exception {

        when(bookService.findAll()).thenReturn(BOOKS_LIST);

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", BOOKS_LIST))
                .andExpect(view().name("list"));
    }

    @Test
    public void testEditPage() throws Exception {

        long id = 1L;
        Author author = new Author(1, "A_1");
        Genre genre = new Genre(1, "A_2");
        Book book = new Book(1, "Book_1", author, genre);


        List<Author> authors = List.of(author);
        List<Genre> genres = List.of(genre);

        BookDto bookDto = new BookDto();
        bookDto.setId(id);
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthorId(genre.getId());
        bookDto.setGenreId(genre.getId());


        when(bookService.findById(id)).thenReturn(Optional.of(book));
        when(authorService.findAll()).thenReturn(authors);
        when(genreService.findAll()).thenReturn(genres);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        mvc.perform(get("/edit").param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", bookDto))
                .andExpect(model().attribute("authors", authors))
                .andExpect(model().attribute("genres", genres))
                .andExpect(view().name("edit"));
    }

    @Test
    public void testCreatePage() throws Exception {

        Author author = new Author(1, "a_2");
        Genre genre = new Genre(1, "g_2");

        List<Author> authors = List.of(author);
        List<Genre> genres = List.of(genre);

        when(authorService.findAll()).thenReturn(authors);
        when(genreService.findAll()).thenReturn(genres);

        mvc.perform(get("/create"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", new BookDto()))
                .andExpect(model().attribute("authors", authors))
                .andExpect(model().attribute("genres", genres))
                .andExpect(view().name("create"));
    }

    @Test
    public void shouldCallInsertMethod() throws Exception {
        String title = "Title";
        long authorId = 1L;
        long genreId = 2L;
        mvc.perform(post("/create")
                        .param("title", title)
                        .param("authorId", String.valueOf(authorId))
                        .param("genreId", String.valueOf(genreId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        verify(bookService).insert(title, authorId, genreId);
    }

    @Test
    public void shouldCallUpdateMethod() throws Exception {
        long id = 1L;
        String title = "Title";
        long authorId = 2L;
        long genreId = 3L;
        mvc.perform(post("/edit")
                        .param("id", String.valueOf(id))
                        .param("title", title)
                        .param("authorId", String.valueOf(authorId))
                        .param("genreId", String.valueOf(genreId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        verify(bookService).update(id, title, authorId, genreId);
    }
}