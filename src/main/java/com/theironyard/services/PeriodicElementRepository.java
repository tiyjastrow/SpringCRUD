package com.theironyard.services;

import com.theironyard.PeriodicElement;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Zach on 10/10/16.
 */
public interface PeriodicElementRepository extends CrudRepository<PeriodicElement,Integer> {
    List<PeriodicElement> findAllByOrderById();
    PeriodicElement findFirstById(Integer id);

}
