package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final BookMapper bookMapper;

    @GetMapping("/")
    public String listPage(Model model) {

        List<Book> books = bookService.findAll();

        model.addAttribute("books", books);

        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") long id, Model model) {

        Book book = bookService
                .findById(id)
                .orElseThrow(NotFoundException::new);

        BookDto bookDto = bookMapper.toDto(book);

        List<Author> authors = authorService.findAll();
        List<Genre> genres = genreService.findAll();

        model.addAttribute("book", bookDto);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);

        return "edit";
    }

    @GetMapping("/create")
    public String createPage(Model model) {

        List<Author> authors = authorService.findAll();
        List<Genre> genres = genreService.findAll();

        model.addAttribute("book", new BookDto());
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);

        return "create";
    }

    @PostMapping("/create")
    public String saveBook(@Valid @ModelAttribute("book") BookDto bookDto,
                           BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            List<Author> authors = authorService.findAll();
            List<Genre> genres = genreService.findAll();

            model.addAttribute("authors", authors);
            model.addAttribute("genres", genres);

            return "create";
        }

        bookService.insert(bookDto.getTitle(),
                bookDto.getAuthorId(),
                bookDto.getGenreId());

        return "redirect:/";
    }

    @PostMapping("/edit")
    public String editBook(@Valid @ModelAttribute("book") BookDto bookDto,
                           BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            List<Author> authors = authorService.findAll();
            List<Genre> genres = genreService.findAll();

            model.addAttribute("authors", authors);
            model.addAttribute("genres", genres);
            return "edit";
        }

        bookService.update(bookDto.getId(),
                bookDto.getTitle(),
                bookDto.getAuthorId(),
                bookDto.getGenreId());

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deletePage(@PathVariable Long id, Model model) {
        Book book = bookService.findById(id).orElseThrow();
        model.addAttribute("book", book);
        return "delete";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }
}