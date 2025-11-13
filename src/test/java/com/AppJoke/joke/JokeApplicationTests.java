package com.AppJoke.joke;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.AppJoke.joke.dto.JokeDto;
import com.AppJoke.joke.entites.Category;
import com.AppJoke.joke.entites.Joke;
import com.AppJoke.joke.mapper.JokeMapper;
import com.AppJoke.joke.repository.CategoryRepository;
import com.AppJoke.joke.repository.JokeRepository;
import com.AppJoke.joke.service.JokeService;

@SpringBootTest
class JokeApplicationTests {

    @Autowired
    private JokeRepository jokeRepository;
    @Autowired
    private JokeService jokeService;
    @Autowired
    private CategoryRepository categoryRepository;

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
        joke1.setCategory(category);
        jokeRepository.save(joke1);

        Joke joke2 = new Joke();
        joke2.setJokeContent("jokeContent2");
        joke2.setJokeAnswer("jokeAnswer2");
        joke2.setCategory(category);
        jokeRepository.save(joke2);
    }

    @Test
    public void testSaveJoke() {
        Joke joke = new Joke();
        joke.setJokeContent("jokeContent");
        joke.setJokeAnswer("jokeAnswer");
        Category category = categoryRepository.findAll().get(0);
        joke.setCategory(category);
        JokeDto jokeDto = JokeMapper.toDto(joke);
        jokeService.saveJoke(jokeDto);

        Optional<Joke> retrievedJoke = jokeRepository.findById(jokeRepository.findAll().get(2).getId());
        assertTrue(retrievedJoke.isPresent());
        assertEquals("jokeContent", retrievedJoke.get().getJokeContent());
        assertEquals("jokeAnswer", retrievedJoke.get().getJokeAnswer());
    }

    @Test
    public void testListAllJokes() {
        assertEquals(2, jokeService.listJokes().size());
    }

    @Test
    public void testFindJokeById() {
        Joke joke = jokeRepository.findAll().get(0);
        JokeDto retrievedJoke = jokeService.getJokeDtoById(joke.getId());
        assertTrue(retrievedJoke != null);
        assertEquals("jokeContent1", retrievedJoke.jokeContent());
        assertEquals("jokeAnswer1", retrievedJoke.jokeAnswer());
    }

    @Test
    public void testDeleteJoke() {
        Joke joke = jokeRepository.findAll().get(0);
        jokeService.deleteJoke(joke.getId());
        Optional<Joke> retrievedJoke = jokeRepository.findById(joke.getId());
        assertTrue(retrievedJoke.isEmpty());
    }

    @Test
    public void testUpdateJoke() {
        Joke joke = jokeRepository.findAll().get(0);
        Long jokeId = joke.getId();
        joke.setJokeContent("updatedContent");
        joke.setJokeAnswer("updatedAnswer");
        jokeService.updateJoke(jokeId, JokeMapper.toDto(joke));
        Optional<Joke> retrievedJoke = jokeRepository.findById(jokeId);
        assertTrue(retrievedJoke.isPresent());
        assertEquals("updatedContent", retrievedJoke.get().getJokeContent());
        assertEquals("updatedAnswer", retrievedJoke.get().getJokeAnswer());
    }

    @Test
    public void testGetRandomJoke() {
        JokeDto randomJoke = jokeService.getRandomJoke();
        assertTrue(randomJoke != null);
        assertTrue(randomJoke.jokeContent().equals("jokeContent1") || randomJoke.jokeContent().equals("jokeContent2"));
    }

}
