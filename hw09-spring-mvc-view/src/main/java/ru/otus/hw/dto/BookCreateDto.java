package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCreateDto {

    @PositiveOrZero
    private long id;

    @NotBlank(message = "Title field should not be blank")
    @Size(min = 2, max = 10, message = "Title field should be between 2 and 10 characters")
    private String title;

    @Positive
    private long authorId;

    @Positive
    private long genreId;
}
