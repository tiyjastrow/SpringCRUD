package com.bharris.services;

import com.bharris.entities.Item;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Integer>{
    List<Item> findAllByOrderByLocalDateDesc();
    List<Item> findAllByUser_Name(String name);
    @Transactional
    @Modifying
    @Query("update Item i set i.description = ?1 where i.id = ?2")
    void updateDescription(String description, int id);



}
