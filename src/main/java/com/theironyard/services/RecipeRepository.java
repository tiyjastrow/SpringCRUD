package com.theironyard.services;

import com.theironyard.entities.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface RecipeRepository extends CrudRepository<Recipe, Integer>{

    List<Recipe> findAll();

}
