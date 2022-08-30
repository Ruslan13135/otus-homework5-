package ot.homework5plus.rushm.controllers;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.annotation.Bulkhead.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ot.homework5plus.rushm.models.entity.Genre;
import ot.homework5plus.rushm.service.GenreService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class GenreController {

    private static final String GENRE_SERVICE = "genreService";

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    // Получить все жанры
    @GetMapping("/api/genre")
    @Bulkhead(name = GENRE_SERVICE, fallbackMethod = "bulkHeadGetAllGenre", type = Type.SEMAPHORE)
    public ResponseEntity<List<Genre>> getGenres() {
        return new ResponseEntity<>(genreService.getAll(), HttpStatus.OK);
    }

    // Получить жанр
    @GetMapping("/api/genre/{id}")
    @Bulkhead(name = GENRE_SERVICE, fallbackMethod = "bulkHeadGetGenre", type = Type.SEMAPHORE)
    public ResponseEntity<Genre> getGenres(@PathVariable("id") Long genreId) {
        if (genreId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Genre genre = genreService.getGenre(genreId);
        return new ResponseEntity<>(genre, HttpStatus.OK);
    }

    // Создать новый жанр
    @PostMapping("/api/genre")
    @Bulkhead(name = GENRE_SERVICE, fallbackMethod = "bulkHeadGetGenre", type = Type.SEMAPHORE)
    public ResponseEntity<Genre> addGenre(@RequestBody Genre requestGenre) {
        if (requestGenre == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        requestGenre.setId(null);
        Genre genre = genreService.addGenre(requestGenre.getName());
        if (genre == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(genre, HttpStatus.OK);
    }

    // Обновить жанр
    @PutMapping("/api/genre/{id}")
    @Bulkhead(name = GENRE_SERVICE, fallbackMethod = "bulkHeadGetGenre", type = Type.SEMAPHORE)
    public ResponseEntity<Genre> updateGenre(@PathVariable("id") Long id, @RequestBody Genre requestGenre) {
        if (requestGenre == null || requestGenre.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        requestGenre.setId(id);
        Genre genre = genreService.getGenre(requestGenre.getId());
        if (genre == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        genre = genreService.updateGenre(requestGenre);
        if (genre == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(genre, HttpStatus.OK);
    }

    // Удалить жанр
    @DeleteMapping("/api/genre/{id}")
    @Bulkhead(name = GENRE_SERVICE, fallbackMethod = "bulkHeadDelGenre", type = Type.SEMAPHORE)
    public ResponseEntity<String> deleteGenre(@PathVariable("id") Long genreId) {
        if (genreId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Genre genre = genreService.getGenre(genreId);
        if (genre == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        genreService.deleteGenre(genreId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<List<Genre>> bulkHeadGetAllGenre(Exception t) {
        log.info("bulkHeadGetAllGenre");
        List<Genre> genre = new ArrayList<>();
        genre.add(new Genre(1L, "Жанр"));
        return new ResponseEntity<>(genre, HttpStatus.OK);
    }

    public ResponseEntity<Genre> bulkHeadGetGenre(Exception t) {
        log.info("bulkHeadGetGenre");
        Genre genre = new Genre(1L, "Жанр");
        return new ResponseEntity<>(genre, HttpStatus.OK);
    }

    public ResponseEntity<String> bulkHeadDelGenre(Exception t) {
        log.info("bulkHeadDelGenre");
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
