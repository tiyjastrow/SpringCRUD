package com.theironyard.controllers;

import com.theironyard.entities.User;
import com.theironyard.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class SpringCrudController {
    @Autowired
    UserRepository users;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        User user = users.findFirstByUsername(username);
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String username, String password) throws Exception {
        if (username != null && password != null) {
            User user = users.findFirstByUsername(username);
            if (user == null) {
                users.save(new User(username, password));
            } else if (!user.verifyPassword(password)) {
                throw new Exception("Login failed!");
            }
            session.setAttribute("username", username);
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
