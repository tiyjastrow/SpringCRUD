package com.theironyard.services;

import com.theironyard.entities.Event;
import com.theironyard.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 10/10/16.
 */
public interface EventRepository extends CrudRepository<Event, Integer> {
    ArrayList<Event> findAllByOrderByDateTimeDesc();
    ArrayList<Event> findByUser(User user);
}
