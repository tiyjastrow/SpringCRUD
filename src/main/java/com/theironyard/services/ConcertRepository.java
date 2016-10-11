package com.theironyard.services;

import com.theironyard.entities.Concert;
import com.theironyard.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by jakefroeb on 10/10/16.
 */
public interface ConcertRepository extends CrudRepository<Concert,Integer>{
    List<Concert> findAll();
    Concert findFirstById(int id);

}
