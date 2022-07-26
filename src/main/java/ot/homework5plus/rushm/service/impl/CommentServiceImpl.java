package ot.homework5plus.rushm.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Comment;
import ot.homework5plus.rushm.repository.CommentRepository;
import ot.homework5plus.rushm.service.CachedDataService;
import ot.homework5plus.rushm.service.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CachedDataService cachedDataService;

    @Override
    @HystrixCommand(groupKey = "CommentService", commandKey = "findAllCommentByBook", fallbackMethod = "getCachedComments")
    public List<Comment> findAllComments(Book book) {
        return commentRepository.findAllByBook(book);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public void addOrSaveComment(Comment comment) {
        commentRepository.save(comment);
    }

    private List<Comment> getCachedComments(Book book) {
        return cachedDataService.getCachedComments();
    }
}
