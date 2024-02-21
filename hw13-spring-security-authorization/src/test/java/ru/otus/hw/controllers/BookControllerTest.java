package ru.otus.hw.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.*;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.*;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@Import(SecurityConfiguration.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private BookMapper bookMapper;

    @MockBean
    private CommentService commentService;

    @MockBean
    private UserSecurityDataService userSecurityDataService;

    private final static List<BookDto> BOOKS_LIST = List.of(new BookDto(1, "b_1", "a_1", "g_1"),
            new BookDto(2, "b_2", "a_2", "g_2"));

    @Test
    @WithMockUser(value = "usr", roles = "USER")
    void shouldForbiddenWhenCallDeleteEndpointWithRoleUser() throws Exception {
        long id = 1L;
        mvc.perform(post("/delete/{id}", id))
                .andExpect(status().is4xxClientError());

    }

    @Test
    @WithMockUser(value = "usr", roles = "ADMIN")
    void shouldCallDeleteMethodWithRoleAdmin() throws Exception {
        long id = 1L;
        mvc.perform(post("/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        verify(bookService).deleteById(id);
    }


    @Test
    @WithMockUser("usr")
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
    @WithMockUser(value = "usr")
    public void testListPageAnyRole() throws Exception {

        when(bookService.findAll()).thenReturn(BOOKS_LIST);

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", BOOKS_LIST))
                .andExpect(view().name("list"));
    }

    @Test
    public void testListPageShouldRedirectUnauthorized() throws Exception {

        mvc.perform(get("/"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(value = "usr", roles = "ADMIN")
    public void testEditPageAdmin() throws Exception {

        long id = 1L;
        AuthorDto author = new AuthorDto(1, "A_1");
        GenreDto genre = new GenreDto(1, "A_2");
        BookUpdateDto book = new BookUpdateDto(1L, "Book_1", author.getId(), genre.getId());


        List<AuthorDto> authors = List.of(author);
        List<GenreDto> genres = List.of(genre);

        when(bookService.findById(id)).thenReturn(book);
        when(authorService.findAll()).thenReturn(authors);
        when(genreService.findAll()).thenReturn(genres);

        mvc.perform(get("/edit").param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", book))
                .andExpect(model().attribute("authors", authors))
                .andExpect(model().attribute("genres", genres))
                .andExpect(view().name("edit"));
    }

    @Test
    @WithMockUser(value = "usr", roles = "USER")
    public void testEditPageUser() throws Exception {

        long id = 1L;
        AuthorDto author = new AuthorDto(1, "A_1");
        GenreDto genre = new GenreDto(1, "A_2");
        BookUpdateDto book = new BookUpdateDto(1L, "Book_1", author.getId(), genre.getId());


        List<AuthorDto> authors = List.of(author);
        List<GenreDto> genres = List.of(genre);

        when(bookService.findById(id)).thenReturn(book);
        when(authorService.findAll()).thenReturn(authors);
        when(genreService.findAll()).thenReturn(genres);

        mvc.perform(get("/edit").param("id", String.valueOf(id)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    @WithMockUser(value = "usr",roles = "ADMIN")
    public void testCreatePage() throws Exception {

        AuthorDto author = new AuthorDto(1, "a_2");
        GenreDto genre = new GenreDto(1, "g_2");

        List<AuthorDto> authors = List.of(author);
        List<GenreDto> genres = List.of(genre);

        when(authorService.findAll()).thenReturn(authors);
        when(genreService.findAll()).thenReturn(genres);

        mvc.perform(get("/create"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", new BookCreateDto()))
                .andExpect(model().attribute("authors", authors))
                .andExpect(model().attribute("genres", genres))
                .andExpect(view().name("create"));
    }

    @Test
    @WithMockUser(value = "usr",roles = {"ADMIN", "USER"})
    public void shouldCallCreateEndpointAdminAndUserRoles() throws Exception {

        BookCreateDto bookCreateDto = new BookCreateDto("Title",2L,3L);

        mvc.perform(post("/create")
                        .param("title", bookCreateDto.getTitle())
                        .param("authorId", String.valueOf(bookCreateDto.getAuthorId()))
                        .param("genreId", String.valueOf(bookCreateDto.getGenreId())))
                .andExpect(status().is3xxRedirection());

        verify(bookService).create(bookCreateDto);
    }

    @Test
    @WithMockUser(value = "usr",roles = "USER")
    public void shouldForbiddenWhenCallUpdateEndpoint() throws Exception {

        BookUpdateDto bookUpdateDto = new BookUpdateDto(1L,"Title",2L,3L);

        mvc.perform(post("/edit")
                        .param("id", String.valueOf(bookUpdateDto.getId()))
                        .param("title", bookUpdateDto.getTitle())
                        .param("authorId", String.valueOf(bookUpdateDto.getAuthorId()))
                        .param("genreId", String.valueOf(bookUpdateDto.getGenreId())))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(value = "usr",roles = "ADMIN")
    public void shouldCallUpdateEndpointAdmin() throws Exception {

        BookUpdateDto bookUpdateDto = new BookUpdateDto(1L,"Title",2L,3L);

        mvc.perform(post("/edit")
                        .param("id", String.valueOf(bookUpdateDto.getId()))
                        .param("title", bookUpdateDto.getTitle())
                        .param("authorId", String.valueOf(bookUpdateDto.getAuthorId()))
                        .param("genreId", String.valueOf(bookUpdateDto.getGenreId())))
                .andExpect(status().is3xxRedirection());

        verify(bookService).update(bookUpdateDto);
    }
}
