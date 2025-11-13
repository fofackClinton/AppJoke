package com.AppJoke.joke.integrationTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.AppJoke.joke.entites.Category;
import com.AppJoke.joke.entites.Joke;
import com.AppJoke.joke.repository.CategoryRepository;
import com.AppJoke.joke.repository.JokeRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class JokeApiTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private JokeRepository jokeRepository;

    @BeforeEach
    public void setUp() {
        jokeRepository.deleteAll();
        categoryRepository.deleteAll();

        Category category = new Category();
        category.setCategoryName("General");
        categoryRepository.save(category);

        Joke joke1 = new Joke();
        joke1.setJokeContent("jokeContent1");
        joke1.setJokeAnswer("jokeAnswer1");
        Category categoryDb = categoryRepository.findAll().get(0);
        joke1.setCategory(categoryDb);
        jokeRepository.save(joke1);

        Joke joke2 = new Joke();
        joke2.setJokeContent("jokeContent2");
        joke2.setJokeAnswer("jokeAnswer2");
        joke2.setCategory(categoryDb);
        jokeRepository.save(joke2);

        Joke joke = new Joke();
        joke.setJokeContent("Old Content");
        joke.setJokeAnswer("Old Answer");
        joke.setCategory(category);
        jokeRepository.save(joke);
    }

    @Test
    public void testGetAllJokes() throws Exception {
        mockMvc.perform(get("/jokes/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("["
                        + "{\"id\":" + jokeRepository.findAll().get(0).getId() + ",\"jokeContent\":\"jokeContent1\",\"jokeAnswer\":\"jokeAnswer1\",\"category\":{\"id\":" + categoryRepository.findAll().get(0).getId() + ",\"categoryName\":\"General\"}},"
                        + "{\"id\":" + jokeRepository.findAll().get(1).getId() + ",\"jokeContent\":\"jokeContent2\",\"jokeAnswer\":\"jokeAnswer2\",\"category\":{\"id\":" + categoryRepository.findAll().get(0).getId() + ",\"categoryName\":\"General\"}},"
                        + "{\"id\":" + jokeRepository.findAll().get(2).getId() + ",\"jokeContent\":\"Old Content\",\"jokeAnswer\":\"Old Answer\",\"category\":{\"id\":" + categoryRepository.findAll().get(0).getId() + ",\"categoryName\":\"General\"}}"
                        + "]"));
    }

    @Test
    public void testGetRandomJoke() throws Exception {
        mockMvc.perform(get("/jokes/random"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    boolean match1 = json.contains("\"jokeContent\":\"jokeContent1\"")
                            && json.contains("\"jokeAnswer\":\"jokeAnswer1\"");
                    boolean match2 = json.contains("\"jokeContent\":\"jokeContent2\"")
                            && json.contains("\"jokeAnswer\":\"jokeAnswer2\"");
                    boolean match3 = json.contains("\"jokeContent\":\"Old Content\"")
                            && json.contains("\"jokeAnswer\":\"Old Answer\"");
                    assertTrue(match1 || match2 || match3, "La réponse ne correspond à aucun des cas attendus");
                });
    }

    @Test
    public void testDeleteJoke() throws Exception {
        Long jokeId = jokeRepository.findAll().get(0).getId();

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                .delete("/jokes/{id}", jokeId))
                .andExpect(status().isOk());

        assertFalse(jokeRepository.findById(jokeId).isPresent());
    }

    @Test
    public void testGetJokeById() throws Exception {
        Category category = categoryRepository.findAll().get(0);
        Long jokeId = jokeRepository.findAll().get(0).getId();

        mockMvc.perform(get("/jokes/{id}", jokeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{"
                        + "\"id\":" + jokeId + ","
                        + "\"jokeContent\":\"jokeContent1\","
                        + "\"jokeAnswer\":\"jokeAnswer1\","
                        + "\"category\":{\"id\":" + category.getId() + ",\"categoryName\":\"General\"}"
                        + "}"));
    }

    @Test
    public void testUpdateJoke() throws Exception {
        Category category = categoryRepository.findAll().get(0);

        Long jokeId = jokeRepository.findAll().get(0).getId();

        String updatedJokeJson = "{\"jokeContent\":\"New Content\",\"jokeAnswer\":\"New Answer\",\"category\":{\"id\":"
                + category.getId() + ",\"categoryName\":\"General\"}}";

        mockMvc.perform(
                put("/jokes/{id}", jokeId)
                        .contentType("application/json")
                        .content(updatedJokeJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{\"id\":" + jokeId
                        + ",\"jokeContent\":\"New Content\",\"jokeAnswer\":\"New Answer\",\"category\":{\"id\":"
                        + category.getId() + ",\"categoryName\":\"General\"}}"));
    }

    @Test
    public void testCreateJoke() throws Exception {
        Category category = categoryRepository.findAll().get(0);

        String newJokeJson = "{\"jokeContent\":\"Created Content\",\"jokeAnswer\":\"Created Answer\",\"category\":{\"id\":"
                + category.getId() + ",\"categoryName\":\"General\"}}";

        mockMvc.perform(
                post("/jokes/create")
                        .contentType("application/json")
                        .content(newJokeJson))
                .andExpect(status().isCreated());

        Joke createdJoke = jokeRepository.findAll().stream()
                .filter(joke -> "Created Content".equals(joke.getJokeContent())
                        && "Created Answer".equals(joke.getJokeAnswer()))
                .findFirst()
                .orElse(null);

        assertTrue(createdJoke != null, "La blague n'a pas été créée avec succès");
    }

}
