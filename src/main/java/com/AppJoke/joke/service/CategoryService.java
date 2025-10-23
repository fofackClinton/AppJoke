package com.AppJoke.joke.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.AppJoke.joke.dto.CategoryDto;
import com.AppJoke.joke.mapper.CategoryMapper;
import com.AppJoke.joke.repository.CategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void saveCategory(CategoryDto categoryDto) {
        log.info("Saving a new Category");
        categoryRepository.save(CategoryMapper.toEntity(categoryDto));
    }

    public void deleteCategory(Long id) {
        log.info("Deleting Category with id: {}", id);
        categoryRepository.deleteById(id);
    }

    public CategoryDto getCategoryById(Long id){
        log.info("Getting Category by id: {}", id);
        return CategoryMapper.toDto(categoryRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Category not found with id: " + id)));
    }

    public List<CategoryDto> listCategories() {
        log.info("Listing All Categories");
        return categoryRepository.findAll().stream()
            .map(CategoryMapper::toDto)
            .collect(Collectors.toList());
    }

    public CategoryDto getCategoryByName(String categoryName){
        log.info("Getting Category by Name: {}", categoryName);
        return CategoryMapper.toDto(categoryRepository.findByCategoryName(categoryName)
            .orElseThrow(() -> new RuntimeException("Category not found with name: " + categoryName)));
    }

    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        if(id == null) {
            throw new IllegalArgumentException("Category ID cannot be null for update.");
        }   

        log.info("Updating Category with id: {}", id);
        categoryRepository.findById(id).ifPresent(category -> {
            category.setCategoryName(categoryDto.categoryName());
            categoryRepository.save(category);
        });
        return getCategoryById(id);
    }

}
