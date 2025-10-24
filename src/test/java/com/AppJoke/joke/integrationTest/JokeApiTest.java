package com.AppJoke.joke.integrationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.AppJoke.joke.entites.Category;
import com.AppJoke.joke.repository.CategoryRepository;
import com.AppJoke.joke.repository.JokeRepository;
import com.AppJoke.joke.service.JokeService;

@SpringBootTest
@AutoConfigureMockMvc
public class JokeApiTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JokeService jokeService;
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
    }

    @Test
    public void testGetAllJokes() throws Exception {
        mockMvc.perform(get("/jokes"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"));
        // Implement test logic for getting all jokes via API
    }


}
