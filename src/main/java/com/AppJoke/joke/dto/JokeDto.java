package com.AppJoke.joke.dto;

public record JokeDto(
    Long id,
    String jokeContent,
    String jokeAnswer,
    CategoryDto category
) {

}
