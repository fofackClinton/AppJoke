package com.AppJoke.joke.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AppJoke.joke.entites.Joke;

@Repository
public interface JokeRepository extends JpaRepository<Joke, Long >{
    List<Joke> findByCategoryId(Long categoryId);
}
