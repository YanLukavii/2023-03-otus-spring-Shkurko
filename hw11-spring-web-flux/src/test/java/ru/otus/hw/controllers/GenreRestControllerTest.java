package ru.otus.hw.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.GenreService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreRestController.class)
public class GenreRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GenreService genreService;

    private final static List<GenreDto> GENRE_LIST = List.of(new GenreDto(1,"g_1"),
            new GenreDto(2,"g_2"));

    @Test
    void shouldReturnCorrectGenreList() throws Exception {

        given(genreService.findAll()).willReturn(GENRE_LIST);

        mvc.perform(get("/api/genres"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(GENRE_LIST)));
    }

}
