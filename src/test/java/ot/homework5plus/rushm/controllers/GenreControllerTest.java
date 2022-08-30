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
import ot.homework5plus.rushm.models.entity.Genre;
import ot.homework5plus.rushm.service.GenreService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер жанров должен")
@WebMvcTest(GenreController.class)
@AutoConfigureMockMvc(addFilters = false)
class GenreControllerTest {

    private static final String REQUEST_JSON_ALL = "[{\"id\":\"1\",\"name\":\"Test\"},{\"id\":\"2\",\"name\":\"Test2\"},{\"id\":\"3\",\"name\":\"Test3\"},{\"id\":\"4\",\"name\":\"Test4\"}]";
    private static final String REQUEST_JSON_ONE = "{\"id\":\"1\",\"name\":\"Test\"}";
    private static final String REQUEST_JSON_ADD = "{\"id\":\"10\",\"name\":\"Test\"}";
    private static final String REQUEST_JSON_UPDATE = "{\"id\":\"1\",\"name\":\"Test\"}";

    @Autowired
    private MockMvc mockMvc;

    @MockBean private GenreService service;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("возвращать все жанры")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void getGenres() throws Exception {
        List<Genre> genres = List.of(
            new Genre(1L, "Test"),
            new Genre(2L, "Test2"),
            new Genre(3L, "Test3"),
            new Genre(4L, "Test4")
        );
        Mockito.when(service.getAll()).thenReturn(genres);
        this.mockMvc
            .perform(get("/api/genre"))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().json(REQUEST_JSON_ALL));
    }

    @DisplayName("возвращать жанр по его ID")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void testGetGenres() throws Exception {
        Genre genre = new Genre(1L, "Test");
        Mockito.when(service.getGenre(1L)).thenReturn(genre);
        this.mockMvc
            .perform(get("/api/genre/1"))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().json(REQUEST_JSON_ONE));
    }

    @DisplayName("добавлять жанр")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void addGenre() throws Exception {
        Genre newGenre = new Genre(0L, "Test");
        Genre actualGenre = new Genre(10L, "Test2");
        Mockito.when(service.addGenre(newGenre.getName())).thenReturn(actualGenre);
        this.mockMvc
            .perform(post("/api/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newGenre)))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().json(REQUEST_JSON_ADD));
    }

    @DisplayName("изменять жанр")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void updateGenre() throws Exception {
        Genre genre = new Genre(1L, "Test");
        Genre updatedGenre = new Genre(1L, "Test2");
        Mockito.when(service.getGenre(1L)).thenReturn(genre);
        Mockito.when(service.updateGenre(updatedGenre)).thenReturn(updatedGenre);
        this.mockMvc
            .perform(put("/api/genre/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedGenre)))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().json(REQUEST_JSON_UPDATE));
    }

    @DisplayName("удалять жанр")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void deleteGenre() throws Exception {
        Genre genre = new Genre(1L, "Test");
        Mockito.when(service.getGenre(1L)).thenReturn(genre);
        // Delete Author
        this.mockMvc
            .perform(delete("/api/genre/1"))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(status().isOk());
    }
}
