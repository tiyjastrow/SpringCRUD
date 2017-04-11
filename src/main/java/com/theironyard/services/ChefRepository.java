package com.theironyard.services;

import com.theironyard.entities.Chef;
import org.springframework.data.repository.CrudRepository;


public interface ChefRepository extends CrudRepository<Chef, Integer> {
    Chef findFirstByuserName(String userName);

}
