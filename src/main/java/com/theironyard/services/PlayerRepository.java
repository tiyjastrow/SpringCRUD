package com.theironyard.services;

import com.theironyard.entities.Player;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by joe on 11/10/2016.
 */
public interface PlayerRepository extends CrudRepository<Player, Integer> {

    Player findByUserName(String name);
    Player findById(int id);
}
