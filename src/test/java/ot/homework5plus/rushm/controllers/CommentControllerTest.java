package ot.homework5plus.rushm.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ot.homework5plus.rushm.models.entity.Author;
import ot.homework5plus.rushm.models.entity.Book;
import ot.homework5plus.rushm.models.entity.Comment;
import ot.homework5plus.rushm.service.CommentService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер комментариев должен")
@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
class CommentControllerTest {

    private static final String REQUEST_JSON_ALL = "[{\"id\":\"1\",\"postDate\":\"01.01.1900\",\"authorName\":\"Author1\",\"content\":\"Comment content 1\",\"favorite\":true,\"bookId\":\"1\"},{\"id\":\"2\",\"postDate\":\"01.01.1900\",\"authorName\":\"Author2\",\"content\":\"Comment content 2\",\"favorite\":false,\"bookId\":\"1\"}]";
    private static final String REQUEST_JSON_ONE = "{\"id\":\"2\",\"postDate\":\"01.01.1900\",\"authorName\":\"Author2\",\"content\":\"Comment content 2\",\"favorite\":false,\"bookId\":\"1\"}";
    private static final String REQUEST_JSON_ADD = "{\"id\":\"1\",\"postDate\":\"01.01.1900\",\"authorName\":\"Author1\",\"content\":\"Comment content 3\",\"favorite\":true,\"bookId\":\"1\"}";
    ;

    @Autowired
    private MockMvc mockMvc;

    @MockBean private CommentService service;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("возвращать все комментарии для книги")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void getComments() throws Exception {
        List<Comment> comments = List.of(
            new Comment(1L, "01.01.1900", "Author1", "Comment content 1", true, new Book()),
            new Comment(2L, "01.01.1900", "Author2", "Comment content 2", false, new Book())
        );
        Mockito.when(service.getCommentsByBookId(1L)).thenReturn(comments);
        this.mockMvc
            .perform(get("/api/comment/book/1"))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().json(REQUEST_JSON_ALL));
    }

    @DisplayName("возвращать комментарий")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void getComment() throws Exception {
        Comment comment = new Comment(2L, "01.01.1900", "Author2", "Comment content 2", false, new Book());
        Mockito.when(service.getComment(2L)).thenReturn(comment);
        this.mockMvc
            .perform(get("/api/comment/2"))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().json(REQUEST_JSON_ONE));
    }

    @DisplayName("добавлять комментарий для книги")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void addComment() throws Exception {
        Comment newComment = new Comment(0L, "01.01.1900", "Author1", "Comment content 3", true, new Book(1L, "test", new Author(), List.of(), List.of(), List.of()));
        this.mockMvc
            .perform(post("/api/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newComment)))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().json(REQUEST_JSON_ADD));
    }
}
