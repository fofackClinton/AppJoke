package com.AppJoke.joke.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.AppJoke.joke.dto.JokeDto;
import com.AppJoke.joke.entites.Category;
import com.AppJoke.joke.mapper.JokeMapper;
import com.AppJoke.joke.repository.CategoryRepository;
import com.AppJoke.joke.repository.JokeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JokeService {

    private final JokeRepository jokeRepository;
    private final CategoryRepository categoryRepository;

    public JokeService(JokeRepository jokeRepository, CategoryRepository categoryRepository) {
        this.jokeRepository = jokeRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<JokeDto> listJokes() {
        log.info("Listing All Jokes");
        return jokeRepository.findAll().stream()
            .map(JokeMapper::toDto)
            .collect(Collectors.toList());
    }

    public JokeDto getJokeDtoById(Long id){
        log.info("Getting Joke by id: {}", id);
        return JokeMapper.toDto(jokeRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Joke not found with id: " + id)));
    }

    public List<JokeDto> getJokesByCategoryName(String categoryName){
        log.info("Getting Jokes by Category Name: {}", categoryName);
        Category category = categoryRepository.findByCategoryName(categoryName)
            .orElseThrow(() -> new RuntimeException("Category not found with name: " + categoryName));
        return jokeRepository.findByCategory(category).stream()
            .map(JokeMapper::toDto)
            .collect(Collectors.toList()); 
    }

    public JokeDto getRandomJoke() {
        log.info("Getting a Random Joke");
        Long count = jokeRepository.count();
        int index = (int)(Math.random() * count);
        return JokeMapper.toDto(jokeRepository.findAll().get(index));
    }

    public void saveJoke(JokeDto jokeDto) {
        log.info("Saving a new Joke");
        jokeRepository.save(JokeMapper.toEntity(jokeDto));
    }

    public void deleteJoke(Long id) {
        log.info("Deleting Joke with id: {}", id);
        jokeRepository.deleteById(id);
    }

}
