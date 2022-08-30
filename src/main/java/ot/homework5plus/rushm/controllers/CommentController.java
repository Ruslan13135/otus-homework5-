package ot.homework5plus.rushm.controllers;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.annotation.Bulkhead.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ot.homework5plus.rushm.models.entity.Book;
import ot.homework5plus.rushm.models.entity.Comment;
import ot.homework5plus.rushm.service.CommentService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class CommentController {

    private static final String COMMENT_SERVICE = "commentService";

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Получить все комментарии для книги
    @GetMapping("/api/comment/book/{bookId}")
    @Bulkhead(name = COMMENT_SERVICE, fallbackMethod = "bulkHeadGetAllComment", type = Type.SEMAPHORE)
    public ResponseEntity<List<Comment>> getComments(@PathVariable("bookId") Long bookId) {
        List<Comment> comments = commentService.getCommentsByBookId(bookId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // Получить комментарий
    @GetMapping("/api/comment/{id}")
    @Bulkhead(name = COMMENT_SERVICE, fallbackMethod = "bulkHeadGetComment", type = Type.SEMAPHORE)
    public ResponseEntity<Comment> getComment(@PathVariable("id") Long commentId) {
        Comment comment = commentService.getComment(commentId);
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    // Добавить комментарий
    @PostMapping("/api/comment")
    @Bulkhead(name = COMMENT_SERVICE, fallbackMethod = "bulkHeadGetComment", type = Type.SEMAPHORE)
    public ResponseEntity<Comment> addComment(@RequestBody Comment requestComment) {
        if (requestComment == null || requestComment.getBook().getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        requestComment.setId(null);
        Comment comment = commentService.addComment(requestComment);
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    // Обновить комментарий
    @PutMapping("/api/comment/{id}")
    @Bulkhead(name = COMMENT_SERVICE, fallbackMethod = "bulkHeadGetComment", type = Type.SEMAPHORE)
    public ResponseEntity<Comment> updateComment(@PathVariable("id") Long id, @RequestBody Comment requestComment) {
        if (requestComment == null || requestComment.getId() == null || requestComment.getBook().getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        requestComment.setId(id);
        Comment comment = commentService.getComment(requestComment.getId());
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        comment = commentService.updateComment(requestComment);
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }


    @DeleteMapping("/api/comment/{id}")
    @Bulkhead(name = COMMENT_SERVICE, fallbackMethod = "bulkHeadDelComment", type = Type.SEMAPHORE)
    public ResponseEntity<String> deleteComment(@PathVariable("id") Long commentId) {
        if (commentId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Comment comment = commentService.getComment(commentId);
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<List<Comment>> bulkHeadGetAllComment(Exception t) {
        log.info("bulkHeadGetAllComment");
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1L, "01.01.1900", "Герман",
                "Потрясно. Просто в шоке от книги",
                true, new Book()));
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    public ResponseEntity<Comment> bulkHeadGetComment(Exception t) {
        log.info("bulkHeadGetComment");
        Comment comment = new Comment(1L, "01.01.1900", "Герман",
                "Супер, рад такой книге",
                true, new Book());
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    public ResponseEntity<String> bulkHeadDelComment(Exception t) {
        log.info("bulkHeadDelComment");
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
