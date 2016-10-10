package com.theironyard.services;

import com.theironyard.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by lee on 10/10/16.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    User findFirstByName(String name);
}
