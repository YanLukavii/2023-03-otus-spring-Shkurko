
package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.services.BookService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookRestController.class)
public class BookRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private BookMapper bookMapper;

    private final static List<BookDto> BOOKS_LIST = List.of(new BookDto(1, "b_1", "a_1", "g_1"),
            new BookDto(2, "b_2", "a_2", "g_2"));

    @Test
    void shouldReturnCorrectBookList() throws Exception {

        given(bookService.findAll()).willReturn(BOOKS_LIST);

        mvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(BOOKS_LIST)));
    }

    @Test
    void shouldCorrectCallCreateBookMethod() throws Exception {

        BookCreateDto bookCreateDto = new BookCreateDto("Title",  1L, 1L);

        String expectedResult = objectMapper.writeValueAsString(bookCreateDto);

        mvc.perform(post("/api/books")
                .contentType(APPLICATION_JSON)
                .content(expectedResult))
                .andExpect(status().isCreated());

        verify(bookService).create(bookCreateDto);
    }

    @Test
    void shouldCorrectCallUpdateBookMethod() throws Exception {

        BookUpdateDto bookUpdateDto = new BookUpdateDto(1L, "Title",  1L, 1L);

        String expectedResult = objectMapper.writeValueAsString(bookUpdateDto);

        mvc.perform(put("/api/books/1")
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isOk());

        verify(bookService).update(bookUpdateDto);
    }

    @Test
    void shouldCorrectDeleteBook() throws Exception {
        mvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
        verify(bookService, times(1)).deleteById(1L);
    }
}