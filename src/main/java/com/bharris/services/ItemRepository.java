package com.bharris.services;

import com.bharris.entities.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Integer>{
    List<Item> findAllByOrderByLocalDateDesc();
}
