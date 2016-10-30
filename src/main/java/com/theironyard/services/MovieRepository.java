package com.theironyard.services;


import com.theironyard.entities.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Integer> {
    List<Movie> findAllByOrderById();

}
