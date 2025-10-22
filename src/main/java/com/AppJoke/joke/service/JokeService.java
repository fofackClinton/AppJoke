package com.AppJoke.joke.service;

import org.springframework.stereotype.Service;

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

}
