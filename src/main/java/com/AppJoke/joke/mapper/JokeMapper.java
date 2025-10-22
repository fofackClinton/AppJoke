package com.AppJoke.joke.mapper;

import com.AppJoke.joke.dto.JokeDto;
import com.AppJoke.joke.entites.Joke;

public class JokeMapper {

    public static JokeDto toDto(Joke joke) {
        return new JokeDto(
            joke.getId(),
            joke.getJokeContent(),
            joke.getJokeAnswer(),
            joke.getCategory() != null ? CategoryMapper.toDto(joke.getCategory()) : null
        );
    }   

    public static Joke toEntity(JokeDto jokeDto) {
        Joke joke  = new Joke();
        joke.setId(jokeDto.id());
        joke.setJokeContent(jokeDto.jokeContent());
        joke.setJokeAnswer(jokeDto.jokeAnswer());
        if (jokeDto.category() != null) {
            joke.setCategory(CategoryMapper.toEntity(jokeDto.category()));
        }
        return joke;
    }
}
