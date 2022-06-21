package ot.homework5plus.rushm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ot.homework5plus.rushm.domain.Author;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Comment;
import ot.homework5plus.rushm.domain.Genre;
import ot.homework5plus.rushm.service.BookService;
import ot.homework5plus.rushm.service.CommentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final CommentService commentService;

    @GetMapping("/")
    public String books(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/api/")
    public ResponseEntity<List<Book>> returnBooks() {
        List<Book> books = bookService.findAll();
        return new ResponseEntity<>(books, HttpStatus.CREATED);
    }

    @GetMapping("/addBook")
    public String addBook(Model model) {
        model.addAttribute("book", new Book(new Author(), new Genre()));
        return "bookEdit";
    }

    @PostMapping("/addBook")
    public String addBook(@ModelAttribute Book book) {
        bookService.addOrSaveBook(book);
        return "redirect:/";
    }

    @PostMapping("/delete/")
    public void deleteBook(@RequestBody Book book) {
        bookService.delete(book);
    }

    @PostMapping(value = "/edit/", produces = {"application/json"}, consumes = {"application/json"})
    public ModelAndView editBook(@RequestBody Book book) {
        ModelAndView modelAndView = new ModelAndView("bookEdit");
        modelAndView.addObject("book", book);
        return modelAndView;
    }

    @GetMapping("/view/{book}")
    public String showBook(@ModelAttribute Book book, Model model) {
        model.addAttribute("book", book);
        List<Comment> comments = commentService.findAllComments(book);
        model.addAttribute("comments", comments);
        return "index";
    }
}