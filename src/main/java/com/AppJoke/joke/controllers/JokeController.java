package com.AppJoke.joke.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.AppJoke.joke.dto.JokeDto;
import com.AppJoke.joke.service.JokeService;


@RestController
@RequestMapping("/jokes")
public class JokeController {

    private final JokeService jokeService;

    public JokeController(JokeService jokeService) {
        this.jokeService = jokeService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value="/random", produces = APPLICATION_JSON_VALUE)
    public JokeDto getRandomJoke() {
        return jokeService.getRandomJoke();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value="/create", consumes = APPLICATION_JSON_VALUE)
    public void  createJoke(@RequestBody JokeDto dto) {
        jokeService.saveJoke(dto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value="/all", produces = APPLICATION_JSON_VALUE)
    public List<JokeDto> getAllJokes(){
        return jokeService.listJokes();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value="/{id}")
    public void deleteJoke(@PathVariable("id") Long id) {
        jokeService.deleteJoke(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value="/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public JokeDto updateJoke(@PathVariable("id") Long id, @RequestBody JokeDto jokeDto) {
        return jokeService.updateJoke(id, jokeDto);
    }

}
