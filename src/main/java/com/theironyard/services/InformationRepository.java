package com.theironyard.services;

import com.theironyard.entities.Information;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by joe on 11/10/2016.
 */
public interface InformationRepository extends CrudRepository<Information, Integer> {
}
