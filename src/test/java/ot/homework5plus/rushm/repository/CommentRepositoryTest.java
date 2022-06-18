package ot.homework5plus.rushm.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Comment;
import ot.homework5plus.rushm.repository.impl.CommentRepositoryImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({ CommentRepositoryImpl.class })
class CommentRepositoryTest {

    private static final String NEW_COMMENT_TEXT = "Test";
    private static final long FIRST_ID = 1;
    private static final long SECOND_ID = 2;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void saveComment() {
        Book book = em.find(Book.class, SECOND_ID);
        Comment comment = new Comment(NEW_COMMENT_TEXT, book);
        comment = commentRepository.saveOrUpdate(comment);
        Comment actualComment = em.find(Comment.class, comment.getId());
        assertThat(actualComment).isNotNull().matches(b -> !b.getText().equals(""))
                .matches(b -> b.getBook() != null);
    }

    @Test
    void deleteBookNameById() {
        commentRepository.deleteById(SECOND_ID);
        Comment deletedComment = em.find(Comment.class, FIRST_ID);;
        assertThat(deletedComment).isNull();
    }

    @Test
    void editCommentTextById() {
        Comment firstComment = em.find(Comment.class, SECOND_ID);
        String oldText = firstComment.getText();
        em.clear();
        firstComment.setText(NEW_COMMENT_TEXT);
        commentRepository.saveOrUpdate(firstComment);
        Comment updateComment = em.find(Comment.class, SECOND_ID);
        assertThat(updateComment.getText()).isNotEqualTo(oldText).isEqualTo(NEW_COMMENT_TEXT);
    }
}
