package ot.homework5plus.rushm.service.impl;

import org.springframework.stereotype.Service;

import ot.homework5plus.rushm.repository.CommentRepository;
import ot.homework5plus.rushm.service.CommentService;


@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }
}
