package com.bharris.controllers;

import com.bharris.entities.Item;
import com.bharris.entities.User;
import com.bharris.services.ItemRepository;
import com.bharris.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class SpringCrudController {
    @Autowired
    UserRepository users;
    @Autowired
    ItemRepository items;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model){
        String userName = (String) session.getAttribute("userName");
        List<Item> listItems = items.findAllByOrderByLocalDateDesc();
        if(userName != null) {
            User user = users.findFirstByName(userName);
            List<Item> userItems = items.findAllByUser_Name(userName);
            listItems.removeAll(userItems);
            model.addAttribute("userItems", userItems);
            model.addAttribute("user", user);
            model.addAttribute("now",  LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        }
        model.addAttribute("events", listItems);
        return "home";
    }

    @RequestMapping(path="/login", method=RequestMethod.POST)
    public String login(HttpSession session, String name, String password) throws Exception {
        User user = users.findFirstByName(name);
        if (user == null) {
            user = new User(name, password);
            users.save(user);
        } else if (!user.verifyPassword(password)){
                throw new Exception("Login Failed");
        }
            session.setAttribute("userName", name);

        return "redirect:/";
    }

    @RequestMapping(path="/logout", method=RequestMethod.POST)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(path="/create-event", method=RequestMethod.POST)
    public String createEvent(HttpSession session, String description, String localDate){
        String name = (String)session.getAttribute("userName");
        User user = users.findFirstByName(name);
        Item i = new Item(description, LocalDateTime.parse(localDate), user);
        items.save(i);
        return "redirect:/";
    }

    @RequestMapping(path="/update", method=RequestMethod.POST)
    public String updateItem(HttpSession session, int id, String description){
        String name = (String)session.getAttribute("userName");
        items.updateDescription(description, id);

        return "redirect:/";
    }

    @RequestMapping(path="/delete", method=RequestMethod.POST)
    public String deleteMessage(Model model, int id){
        items.delete(id);
        return "redirect:/";
    }

}
