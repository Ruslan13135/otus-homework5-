package ot.homework5plus.rushm.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ot.homework5plus.rushm.dao.impl.CommentDaoImpl;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(CommentDaoImpl.class)
class CommentDaoImplTest {

    private static final String NEW_COMMENT_TEXT = "Test";
    private static final long FIRST_ID = 1;
    private static final long SECOND_ID = 2;

    @Autowired
    private CommentDaoImpl commentDao;

    @Autowired
    private TestEntityManager em;

    @Test
    void saveComment() {
        Book book = em.find(Book.class, FIRST_ID);
        Comment comment = new Comment(NEW_COMMENT_TEXT, book);
        comment = commentDao.save(comment);
        Comment actualComment = em.find(Comment.class, comment.getId());
        assertThat(actualComment).isNotNull().matches(b -> !b.getText().equals(""))
                .matches(b -> b.getBook() != null);
    }

    @Test
    void findAllCommentsByBookId() {
        List<Comment> comments = commentDao.findByBookId(FIRST_ID);
        assertThat(comments).isNotNull().hasSize((int) SECOND_ID)
                .allMatch(comment -> !comment.getText().equals(""))
                .allMatch(comment -> comment.getBook().getTitle() != null);
    }

    @Test
    void deleteBookNameById() {
        commentDao.deleteById(FIRST_ID);
        Comment deletedComment = em.find(Comment.class, FIRST_ID);
        assertThat(deletedComment).isNull();
    }

    @Test
    void editCommentTextById() {
        Comment firstComment = em.find(Comment.class, SECOND_ID);
        String oldText = firstComment.getText();
        em.clear();
        commentDao.updateTextById(SECOND_ID, NEW_COMMENT_TEXT);
        Comment updateComment = em.find(Comment.class, SECOND_ID);
        assertThat(updateComment.getText()).isNotEqualTo(oldText).isEqualTo(NEW_COMMENT_TEXT);
    }
}
