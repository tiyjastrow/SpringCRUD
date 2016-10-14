package com.theironyard.controllers;

import com.theironyard.entities.Book;
import com.theironyard.entities.User;
import com.theironyard.services.BookRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by halleyfroeb on 10/12/16.
 */
@Controller
public class SpringCRUDController {
    @Autowired
    UserRepository users;

    @Autowired
    BookRepository books;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        List<Book> bookList = books.findAll();
        if (username != null) {
            User user = users.findFirstByUsername(username);
            for (Book book : bookList) {
                User sender = book.getSender();
                String senderName = sender.getUsername();
                if (senderName.equalsIgnoreCase(username)) {
                    book.setViewable("not null");
                } else {
                    book.setViewable(null);
                }
            }
        } else {
            for (Book book : bookList) {
                book.setViewable(null);
            }
        }
        Collections.sort(bookList);
        model.addAttribute("books", bookList);
        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(String username, String password, HttpSession session) throws Exception {
        User user = users.findFirstByUsername(username);
        if (user == null) {
            user = new User(username, PasswordStorage.createHash(password));
            users.save(user);
        } else if (!PasswordStorage.verifyPassword(password, user.getPassword())) {
            throw new Exception("wrong password");
        }
        session.setAttribute("username", username);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout")
    public String logout(HttpSession session) throws IOException {
        session.invalidate();
        return "redirect:/";
    }

//    @RequestMapping(path = "/user", method = RequestMethod.GET)
//    public User getUser(HttpSession session) {
//        String username = (String) session.getAttribute("username");
//        return users.findFirstByUsername(username);
//    }

    @RequestMapping(path = "/create-book", method = RequestMethod.POST)
    public String createBook(HttpSession session, String author, String genre, String title) throws Exception {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new Exception("Not logged in!");
        }
        User sender = users.findFirstByUsername(username);
        Book book = new Book();
        book.setAuthor(author);
        book.setGenre(genre);
        book.setTitle(title);
        book.setSender(sender);
        book.setViewable(null);
        books.save(book);
        return "redirect:/";
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public String updateBook(Integer id, String author, String genre, String title) throws IOException {
        Book book = books.findById(id);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setTitle(title);
        books.save(book);
        return "redirect:./";
    }
    @RequestMapping(path ="/delete", method = RequestMethod.POST)
    public String deleteBook(Integer id){
        books.delete(id);
        return "redirect:./";
    }

}
