package ot.homework5plus.rushm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.repository.BookRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class BookController {

    private final BookRepository bookRepository;

    @GetMapping("/books")
    public Flux<Book> findAll() {
        return bookRepository.findAll();
    }

    @PostMapping("/books")
    public Mono<Book> create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PatchMapping("/books")
    public Mono<Book> update(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @GetMapping("/books/{id}")
    public Mono<Book> find(@PathVariable("id") String id) {
        return bookRepository.findById(id);
    }

    @DeleteMapping("/books/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return bookRepository.deleteById(id);
    }
}