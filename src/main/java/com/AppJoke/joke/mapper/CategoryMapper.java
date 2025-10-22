package com.AppJoke.joke.mapper;

import com.AppJoke.joke.dto.CategoryDto;
import com.AppJoke.joke.entites.Category;

public class CategoryMapper {

    public static CategoryDto toDto(Category category) {
        return new CategoryDto(
            category.getId(),
            category.getCategoryName()
        );
    }

    public static Category toEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.id());
        category.setCategoryName(categoryDto.categoryName());
        return category;
    }

}
