package ot.homework5plus.rushm.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ot.homework5plus.rushm.domain.Comment;
import ot.homework5plus.rushm.domain.dto.CommentDTO;
import ot.homework5plus.rushm.repository.BookRepository;

@Component
@RequiredArgsConstructor
public class CommentDTOProcessor implements ItemProcessor<Comment, CommentDTO> {

    private final BookRepository bookRepository;

    public CommentDTO process(Comment comment) throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setText(comment.getText());
        commentDTO.setBookId(bookRepository.findBookByCommentId(comment.getId()).getId());
        return commentDTO;
    }

}
