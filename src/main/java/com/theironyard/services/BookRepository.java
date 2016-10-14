package com.theironyard.services;

import com.theironyard.entities.Book;
import com.theironyard.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by halleyfroeb on 10/12/16.
 */
public interface BookRepository extends CrudRepository<Book, Integer> {
    List<Book> findBySender(User user);
    List<Book> findAll();
    Book findByTitleAndAuthorAndGenre(String title, String Author, String Genre);


    Book findById(Integer id);
}
