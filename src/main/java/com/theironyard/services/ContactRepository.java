package com.theironyard.services;


import com.theironyard.entities.Contact;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContactRepository extends CrudRepository<Contact, Integer>{
    List<Contact> findAllByNameOrderByNameAsc();
}
