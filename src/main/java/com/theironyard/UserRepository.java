package com.theironyard;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by jeremypitt on 10/10/16.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    User findFirstByName(String userName);
}
