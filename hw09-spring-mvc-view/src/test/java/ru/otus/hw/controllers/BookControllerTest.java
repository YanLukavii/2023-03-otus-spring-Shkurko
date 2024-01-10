package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
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

    @Test
    void testDelete() throws Exception {
        long id = 1L;
        mvc.perform(post("/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        verify(bookService).deleteById(id);
    }

    @Test
    void shouldRenderCorrectBookList() throws Exception {

        List<Book> bookList = List.of(new Book(1, "b_1", new Author(1, "a_1"), new Genre(1, "g_1")),
                new Book(2, "b_2", new Author(2, "a_2"), new Genre(2, "g_2")));

        given(bookService.findAll()).willReturn(bookList);

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("b_1")))
                .andExpect(content().string(containsString("a_1")))
                .andExpect(content().string(containsString("g_1")))
                .andExpect(content().string(containsString("b_2")))
                .andExpect(content().string(containsString("a_2")))
                .andExpect(content().string(containsString("g_2")));

    }

/*   @Test
    void shouldReturnCorrectPersonByNameInRequest() throws Exception {
        Person person = new Person(1, "Person1");
        given(repository.findByName(person.getName())).willReturn(List.of(person));
        PersonDto expectedResult = PersonDto.toDto(person);

        mvc.perform(get("/persons").param("name", person.getName()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }*/
/*
    @Test
    void shouldReturnCorrectPersonByIdInPath() throws Exception {
        Person person = new Person(1, "Person1");
        given(repository.findById(1L)).willReturn(Optional.of(person));
        PersonDto expectedResult = PersonDto.toDto(person);

        mvc.perform(get("/persons/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void shouldReturnExpectedErrorWhenPersonNotFound() throws Exception {
        given(repository.findById(1L)).willReturn(Optional.empty());

        mvc.perform(get("/persons").param("name", "Person1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ERROR_STRING));

        mvc.perform(get("/persons/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ERROR_STRING));
    }*/
}