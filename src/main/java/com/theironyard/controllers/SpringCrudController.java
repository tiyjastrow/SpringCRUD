package com.theironyard.controllers;

import com.theironyard.entities.Event;
import com.theironyard.entities.User;
import com.theironyard.services.EventRepository;
import com.theironyard.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 10/10/16.
 */
@Controller
public class SpringCrudController {
    @Autowired
    UserRepository users;

    @Autowired
    EventRepository events;

    public static String warning = "Please enter your Username and Password.";
    public static LocalDateTime thisDate = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        List<Event> userEvents = new ArrayList<>();
        List<Event> eventEntities = events.findAllByOrderByDateTimeDesc();
        if (userName != null) {
            User user = users.findFirstByName(userName);
            model.addAttribute("user", user);
            model.addAttribute("now", LocalDateTime.now().truncatedTo(ChronoUnit.HOURS));
            userEvents = events.findByUser(user);
        }

        eventEntities.removeAll(userEvents);

        model.addAttribute("thisDate", thisDate);
        model.addAttribute("userEvents", userEvents);
        model.addAttribute("events", eventEntities);
        model.addAttribute("warning", warning);

        return "index";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String name, String password) throws Exception {
        User user = users.findFirstByName(name);
        if (user == null) {
            user = new User(name, password);
            users.save(user);
        } else if (! user.isValid(password)) {
            warning = "That is not the correct password for account " + name;
            return "redirect:/";
        }
        warning = "Please enter your Username and Password.";
        session.setAttribute("userName", name);

        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/";
    }

    @RequestMapping(path = "/create-event", method = RequestMethod.POST)
    public String createEvent(HttpSession session, String eventName, String description, String dateTime) {
        String userName = (String) session.getAttribute("userName");
        User user = users.findFirstByName(userName);

        Event event = new Event(eventName, description, LocalDateTime.parse(dateTime), user);
        events.save(event);
        return "redirect:/";
    }

    @RequestMapping(path = "/delete-event", method = RequestMethod.POST)
    public String deleteEvent(int id) {
        events.delete(id);
        return "redirect:/";
    }

    @RequestMapping(path = "/edit-event", method = RequestMethod.POST)
    public String toggleEditMenu(int id) {
        Event event =  events.findOne(id);
        event.toggleIsEditable();
        thisDate = event.getDateTime().truncatedTo(ChronoUnit.HOURS);
        events.save(event);
        return "redirect:/";
    }

    @RequestMapping(path = "/accept-changes", method = RequestMethod.POST)
    public String acceptChanges(int id, String eventName, String description, String dateTime) {
        Event event = events.findOne(id);
        if (!eventName.isEmpty())
            event.setName(eventName);
        if (!description.isEmpty())
            event.setDescription(description);
        event.setDateTime(LocalDateTime.parse(dateTime));
        event.toggleIsEditable();
        events.save(event);
        return "redirect:/";
    }
}
