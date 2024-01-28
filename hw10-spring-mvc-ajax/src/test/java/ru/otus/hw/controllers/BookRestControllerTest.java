
package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookRestController.class)
public class BookRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private BookMapper bookMapper;

    private final static List<BookDto> BOOKS_LIST = List.of(new BookDto(1, "b_1", "a_1", "g_1"),
            new BookDto(2, "b_2", "a_2", "g_2"));


    @Test
    void shouldReturnCorrectBookList() throws Exception {

        given(bookService.findAll()).willReturn(BOOKS_LIST);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(BOOKS_LIST)));
    }


/*    @Test
    void shouldCorrectSaveNewBook() throws Exception {
        //Person person = new Person(1, "Person1");
      //  Book book = new Book(0,"title", new Author(4,"author_4"), new Genre(4,"Genre_4"));
        Book book = new Book(0,"book_4",new Author(1,"Author_1"),new Genre(1,"Genre_4"));
        //BookDto bookDto = bookMapper.to(book);

        BookDto bookDto = new BookDto(4,"Book_4","Author_1","Genre_1");

        BookCreateDto bookCreateDto = new BookCreateDto(4,"Book_4",1,1);
        //given(bookService.create(any())).willReturn(bookDto);
        given(bookService.create(bookCreateDto)).willReturn(bookDto);
        String expectedResult = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(post("/api/books").contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }*/

/*
    @Test
    void shouldCorrectUpdateBook() throws Exception {
        Person person = new Person(1, "Person1");
         BookDto bookDto = BOOKS_LIST.get(0);

        given(bookService.findById(1L)).willReturn(bookDto);
        given(bookService.update(any())).willAnswer(invocation -> invocation.getArgument(0));

        Person expectedPerson = new Person(1, "Person2");
        String expectedResult = mapper.writeValueAsString(PersonDto.toDto(expectedPerson));

        mvc.perform(patch("/persons/{id}/name", 1).param("name", expectedPerson.getName())
                        .content(expectedResult))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }
*/
    @Test
    void shouldCorrectDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk());
        verify(bookService, times(1)).deleteById(1L);
    }
}