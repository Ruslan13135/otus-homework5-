package ot.homework5plus.rushm.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ot.homework5plus.rushm.domain.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class CommentRepositoryTest {

    private static final String NEW_COMMENT_TEXT = "Test";
    private static final long FIRST_ID = 1;
    private static final int FIFTH_ID = 5;
    private static final long SECOND_ID = 2;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void saveComment() {
        Comment comment = new Comment(FIFTH_ID, NEW_COMMENT_TEXT);
        comment = commentRepository.save(comment);
        Comment actualComment = commentRepository.findById(comment.getId()).get();
        assertThat(actualComment).isNotNull().matches(b -> !b.getText().equals(""))
                .matches(b -> b.getText() != null);
    }

    @Test
    void deleteBookNameById() {
        commentRepository.deleteById(SECOND_ID);
        boolean deletedComment = commentRepository.findById(SECOND_ID).isEmpty();
        assertThat(deletedComment).isTrue();
    }

    @Test
    void editCommentTextById() {
        Comment firstComment = commentRepository.findById(FIRST_ID).get();
        String oldText = firstComment.getText();
        firstComment.setText(NEW_COMMENT_TEXT);
        commentRepository.save(firstComment);
        Comment updateComment = commentRepository.findById(FIRST_ID).get();
        assertThat(updateComment.getText()).isNotEqualTo(oldText).isEqualTo(NEW_COMMENT_TEXT);
    }
}
