package com.theironyard.controller;


import com.theironyard.entities.FoodItem;
import com.theironyard.entities.User;
import com.theironyard.services.FoodItemRepository;
import com.theironyard.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class SpringCrudController {

    @Autowired
    UserRepository users;

    @Autowired
    FoodItemRepository food_items;

    final static String LOGGED_IN_USER = "userName";

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session){
        String userName = (String) session.getAttribute(LOGGED_IN_USER);
        User user = users.findFirstByName(userName);
        List<FoodItem>foodItemEntries;
        if (userName!=null) {
            model.addAttribute("user", user);
            model.addAttribute("now", LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
            foodItemEntries = food_items.findByUserName(userName);
            model.addAttribute("food_items", foodItemEntries);
        }

        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String name, String password) throws Exception{
        User user = users.findFirstByName(name);
        if (user == null) {
            users.save(new User(name, password));
        } else if (!user.verifyPassword(password)){
            throw new Exception("Login failed");
        }
        session.setAttribute("userName", name);
        session.setAttribute("user", name);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(path = "/create-item", method = RequestMethod.POST)
    public String createItem(HttpSession session, String foodName, String calories, String consumptionDate){
        String userName = (String) session.getAttribute(LOGGED_IN_USER);
        if (userName != null) {
            FoodItem foodItem = new FoodItem(foodName, Integer.valueOf(calories), LocalDateTime.parse(consumptionDate), users.findFirstByName(userName));
            food_items.save(foodItem);
        }
        return "redirect:/";
    }

    @RequestMapping(path = "delete-item", method = RequestMethod.POST)
    public String deleteItem(int id){
        food_items.delete(id);
        return "redirect:/";
    }

    @RequestMapping(path = "/edit", method = RequestMethod.GET)
    public String editItem(Model model, int id){
        FoodItem item = food_items.findOne(id);
        model.addAttribute("food_items", item);
        model.addAttribute("now", LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        return "edit";
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public String updateItem(HttpSession session, int id, String foodName, String calories, String consumptionDate){
        String userName = (String) session.getAttribute(LOGGED_IN_USER);
        FoodItem item = new FoodItem(id, foodName, Integer.valueOf(calories), LocalDateTime.parse(consumptionDate), users.findFirstByName(userName));
        food_items.save(item);
        return "redirect:/";
    }


}
