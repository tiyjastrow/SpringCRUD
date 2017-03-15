package com.theironyard.services;

import com.theironyard.entities.FoodItem;
import com.theironyard.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by MacbookStudioPro on 3/13/17.
 */
public interface FoodItemRepository extends CrudRepository<FoodItem, Integer> {


    List<FoodItem> findByUserName(String name);
}
