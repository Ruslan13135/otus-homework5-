package ot.homework5plus.rushm.controllers;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.annotation.Bulkhead.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ot.homework5plus.rushm.models.entity.Author;
import ot.homework5plus.rushm.service.AuthorService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class AuthorController {

    private static final String AUTHOR_SERVICE = "authorService";

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // Получить всех авторов
    @GetMapping("/api/author")
    @Bulkhead(name = AUTHOR_SERVICE, fallbackMethod = "bulkHeadGetAllAuthor", type = Type.SEMAPHORE)
    public ResponseEntity<List<Author>> getAuthors() {
        return new ResponseEntity<>(authorService.getAll(), HttpStatus.OK);
    }

    // Получить автора по id
    @GetMapping("/api/author/{id}")
    @Bulkhead(name = AUTHOR_SERVICE, fallbackMethod = "bulkHeadGetAuthor", type = Type.SEMAPHORE)
    public ResponseEntity<Author> getAuthor(@PathVariable("id") Long authorId) {
        Author author = authorService.getAuthor(authorId);
        if (author == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    // Создать нового автора
    @PostMapping("/api/author")
    @Bulkhead(name = AUTHOR_SERVICE, fallbackMethod = "bulkHeadGetAuthor", type = Type.SEMAPHORE)
    public ResponseEntity<Author> addAuthor(@RequestBody Author requestAuthor) {
        if (requestAuthor == null || requestAuthor.getName() == null || requestAuthor.getName().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        requestAuthor.setId(null);
        Author author = authorService.addAuthor(requestAuthor.getName());
        if (author == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    // Обновить автора
    @PutMapping("/api/author/{id}")
    @Bulkhead(name = AUTHOR_SERVICE, fallbackMethod = "bulkHeadGetAuthor", type = Type.SEMAPHORE)
    public ResponseEntity<Author> updateAuthor(@PathVariable("id") Long id, @RequestBody Author requestAuthor) {
        if (requestAuthor == null || requestAuthor.getName() == null || requestAuthor.getName().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        requestAuthor.setId(id);
        Author author = authorService.getAuthor(requestAuthor.getId());
        if (author == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        author = authorService.updateAuthor(requestAuthor);
        if (author == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    // Удалить автора
    @DeleteMapping("/api/author/{id}")
    @Bulkhead(name = AUTHOR_SERVICE, fallbackMethod = "bulkHeadDelAuthor", type = Type.SEMAPHORE)
    public ResponseEntity<String> deleteAuthor(@PathVariable("id") Long authorId) {
        if (authorId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Author author = authorService.getAuthor(authorId);
        if (author == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorService.deleteAuthor(authorId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<List<Author>> bulkHeadGetAllAuthor(Exception t) {
        log.info("bulkHeadGetAllAuthor");
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(0L, "Автор"));
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    public ResponseEntity<Author> bulkHeadGetAuthor(Exception t) {
        log.info("bulkHeadGetAuthor");
        Author author = new Author(0L, "Автор");
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    public ResponseEntity<String> bulkHeadDelAuthor(Exception t) {
        log.info("bulkHeadDelAuthor");
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
