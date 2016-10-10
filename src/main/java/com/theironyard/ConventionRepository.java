package com.theironyard;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeremypitt on 10/10/16.
 */
public interface ConventionRepository extends CrudRepository<Convention, Integer> {
    List<Convention> findAllByOrderById();
    Convention findFirstById(int id);
    ArrayList<Convention> findByUser(User user);
}
