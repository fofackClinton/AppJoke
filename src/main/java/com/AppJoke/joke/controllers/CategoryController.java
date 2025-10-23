package com.AppJoke.joke.controllers;

import org.springframework.http.HttpStatus;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.AppJoke.joke.dto.CategoryDto;
import com.AppJoke.joke.service.CategoryService;

import jakarta.websocket.server.PathParam;




@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(name="/create", consumes = APPLICATION_JSON_VALUE)
    public void createCategory(@RequestBody CategoryDto dto) {
        categoryService.saveCategory(dto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(name="/delete")
    public void deleteCategory(@PathParam("id") Long id) {
        categoryService.deleteCategory(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(name="/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CategoryDto updateCategory(@PathVariable Long id, @RequestBody CategoryDto dto) {
         return categoryService.updateCategory(id, dto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(name="path", produces = APPLICATION_JSON_VALUE)
    public CategoryDto getCategoryById(@RequestParam Long id){
        return categoryService.getCategoryById(id);
    }
    

}
