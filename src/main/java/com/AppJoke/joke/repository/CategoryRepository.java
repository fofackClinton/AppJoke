package com.AppJoke.joke.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AppJoke.joke.entites.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
