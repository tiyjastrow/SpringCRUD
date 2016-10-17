package com.theironyard.services;

import com.theironyard.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by joshuakeough on 10/11/16.
 */
public interface UserRepository extends CrudRepository<User, Integer>{

    User findFirstByUserName(String userName);
}
