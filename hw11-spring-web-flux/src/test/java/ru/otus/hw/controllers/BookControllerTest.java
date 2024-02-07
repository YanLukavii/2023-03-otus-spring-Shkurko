package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public void testListPage() throws Exception {

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"));
    }

    @Test
    public void testEditPage() throws Exception {

        mvc.perform(get("/edit/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(model().attributeExists("id"))
                .andExpect(model().attribute("id", 1L));

    }

    @Test
    public void testCreatePage() throws Exception {

        mvc.perform(get("/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create"));
    }

}