package com.theironyard.controllers;


import com.theironyard.entities.Movie;
import com.theironyard.entities.User;
import com.theironyard.services.MovieRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
public class SpringCrudController {
    @Autowired
    MovieRepository movies;

    @Autowired
    UserRepository users;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        ArrayList<Movie> userMovieList = new ArrayList<>();
        userMovieList = (ArrayList) movies.findAll();

        if (userName != null) {
            User user = users.findFirstByName(userName);
            model.addAttribute("user", user);

            for (Movie m : userMovieList) {
                if (user.getName().equals(m.getUser().getName())) {
                    m.setShow("show");
                }
            }

        }

        model.addAttribute("userMovieList", userMovieList);
        return "home";

    }


    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String name, String password) throws Exception {
        User user = users.findFirstByName(name);
        if (user == null) {
            user = new User(name, PasswordStorage.createHash(password));
            users.save(user);
        } else if (!PasswordStorage.verifyPassword(password, user.getPassword())) {
            throw new Exception("Incorrect password");
        }
        session.setAttribute("userName", name);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(path = "/add-movie", method = RequestMethod.POST)
    public String addMovie(HttpSession session, String movieName, String movieRating, String movieGenre, int movieYear) {
        String userName = (String) session.getAttribute("userName");
        User user = users.findFirstByName(userName);
        Movie movie = new Movie(movieName, movieRating, movieGenre, movieYear, user);
        movies.save(movie);
        return "redirect:/";

    }

    @RequestMapping(path = "/edit", method = RequestMethod.GET)
    public String editMovie(Model model, int id) {
        List<Movie> movieList = movies.findAllByOrderById();
        Movie movie = new Movie();
        for (Movie m : movieList) {
            if (m.id == id) {
                movie = m;
            }
        }
        model.addAttribute("movieToEdit", movie);
        return "edit";
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public String update(String movieName, String movieRating, String movieGenre, int movieYear, int id) {
        Movie editEntry = movies.findOne(id);
        editEntry.name = movieName;
        editEntry.rating = movieRating;
        editEntry.genre = movieGenre;
        editEntry.releaseYear = movieYear;

        movies.save(editEntry);
        return "redirect:/";
    }

    @RequestMapping(path = "/delete-movie", method = RequestMethod.POST)
    public String delete(Integer id) {
        movies.delete(id);
        return "redirect:/";
    }

}

