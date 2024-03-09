package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final CommentService commentService;

    @GetMapping("/")
    public String listPage(Model model) {

        List<BookDto> books = bookService.findAll();

        model.addAttribute("books", books);

        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") long id, Model model) {

        BookUpdateDto bookUpdateDto = bookService
                .findById(id);

        List<AuthorDto> authors = authorService.findAll();
        List<GenreDto> genres = genreService.findAll();

        model.addAttribute("book", bookUpdateDto);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);

        return "edit";
    }

    @GetMapping("/create")
    public String createPage(Model model) {

        List<AuthorDto> authors = authorService.findAll();
        List<GenreDto> genres = genreService.findAll();

        model.addAttribute("book", new BookCreateDto());
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);

        return "create";
    }

    @PostMapping("/create")
    public String saveBook(@Valid @ModelAttribute("book") BookCreateDto bookCreateDto,
                           BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            List<AuthorDto> authors = authorService.findAll();
            List<GenreDto> genres = genreService.findAll();

            model.addAttribute("authors", authors);
            model.addAttribute("genres", genres);

            return "create";
        }
        bookService.create(bookCreateDto);

        return "redirect:/";
    }

    @PostMapping("/edit")
    public String editBook(@Valid @ModelAttribute("book") BookUpdateDto bookUpdateDto,
                           BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            List<AuthorDto> authors = authorService.findAll();
            List<GenreDto> genres = genreService.findAll();

            model.addAttribute("authors", authors);
            model.addAttribute("genres", genres);
            return "edit";
        }

        bookService.update(bookUpdateDto);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deletePage(@PathVariable Long id, Model model) {
        BookUpdateDto book = bookService.findById(id);
        model.addAttribute("book", book);
        return "delete";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/comments")
    public String commentsPage(@RequestParam("id") long id, Model model) {

        BookUpdateDto book = bookService
                .findById(id);

        List<CommentDto> commentList = commentService
                .findByBookId(id);

        model.addAttribute("book", book);
        model.addAttribute("comments", commentList);

        return "comments";
    }

    @GetMapping("/access-denied")
    public String accessDeniedPage() {

        return "access-denied";
    }

}