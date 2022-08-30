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
import ot.homework5plus.rushm.service.AuthorService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер авторов должен")
@WebMvcTest(AuthorController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthorControllerTest {


    private static final String REQUEST_JSON_ALL = "[{\"id\":\"1\",\"name\":\"TestName\"},{\"id\":\"2\",\"name\":\"TestName2\"},{\"id\":\"3\",\"name\":\"TestName3\"},{\"id\":\"4\",\"name\":\"TestName4\"},{\"id\":\"5\",\"name\":\"TestName5\"}]";
    private static final String REQUEST_JSON_ONE = "{\"id\":\"1\",\"name\":\"TestName6\"}";
    private static final String REQUEST_JSON_ADD = "{\"id\":\"10\",\"name\":\"TestName7\"}";
    private static final String REQUEST_JSON_UPDATE = "{\"id\":\"1\",\"name\":\"TestName8\"}";

    @Autowired
    private MockMvc mockMvc;

    @MockBean private AuthorService service;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("возвращать всех авторов")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void getAuthors() throws Exception {

        List<Author> authors = List.of(
            new Author(1L, "TestName1"),
            new Author(2L, "TestName2"),
            new Author(3L, "TestName3"),
            new Author(4L, "TestName4"),
            new Author(5L, "TestName5")
        );
        Mockito.when(service.getAll()).thenReturn(authors);
        this.mockMvc
            .perform(get("/api/author")/*.header("Authorization", "Bearer " + token)*/)
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(REQUEST_JSON_ALL));
    }

    @DisplayName("возвращать автора по его ID")
    @WithMockUser(
        username = "admin",
        authorities = {"ROLE_ADMIN"}
    )
    @Test
    void getAuthor() throws Exception {
        Author author = new Author(1L, "TestName6");
        Mockito.when(service.getAuthor(1L)).thenReturn(author);
        this.mockMvc
            .perform(get("/api/author/1"))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().json(REQUEST_JSON_ONE));
    }

    @DisplayName("добавлять автора")
    @WithMockUser(
        username = "admin",
        authorities = {"ROLE_ADMIN"}
    )
    @Test
    void addAuthor() throws Exception {
        Author newAuthor = new Author(0L, "TestName7");
        Author actualAuthor = new Author(10L, "TestName7");
        Mockito.when(service.addAuthor(newAuthor.getName())).thenReturn(actualAuthor);
        this.mockMvc
            .perform(post("/api/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newAuthor)))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().json(REQUEST_JSON_ADD));
    }

    @DisplayName("изменять автора")
    @WithMockUser(
        username = "admin",
        authorities = {"ROLE_ADMIN"}
    )
    @Test
    void updateAuthor() throws Exception {
        // Add Author
        Author actualAuthor = new Author(1L, "TestName8");
        // Update Author
        Author updatedAuthor = new Author(1L, "TestName8");
        Mockito.when(service.getAuthor(1L)).thenReturn(actualAuthor);
        Mockito.when(service.updateAuthor(updatedAuthor)).thenReturn(updatedAuthor);
        this.mockMvc
            .perform(put("/api/author/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedAuthor)))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().json(REQUEST_JSON_UPDATE));
    }

    @DisplayName("удалять автора")
    @WithMockUser(
        username = "admin",
        authorities = {"ROLE_ADMIN"}
    )
    @Test
    void deleteAuthor() throws Exception {
        Author actualAuthor = new Author(1L, "TestName1");
        Mockito.when(service.getAuthor(1L)).thenReturn(actualAuthor);
        this.mockMvc
            .perform(delete("/api/author/1"))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(status().isOk());
    }

}
