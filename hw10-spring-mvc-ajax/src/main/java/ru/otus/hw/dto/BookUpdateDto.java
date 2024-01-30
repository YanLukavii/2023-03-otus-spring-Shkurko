package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdateDto {

    @Positive
    private long id;

    @NotBlank
    private String title;

    @Positive
    private long authorId;

    @Positive
    private long genreId;

}
