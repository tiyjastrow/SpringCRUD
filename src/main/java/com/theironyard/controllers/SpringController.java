package com.theironyard.controllers;

import com.theironyard.entities.Chef;
import com.theironyard.entities.Recipe;
import com.theironyard.services.ChefRepository;
import com.theironyard.services.RecipeRepository;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SpringController {
    @Autowired
    RecipeRepository recipes;
    @Autowired
    ChefRepository chefs;

    @PostConstruct
    public void init() throws PasswordStorage.CannotPerformOperationException {
        if (chefs.count() == 0) {
            Chef chef = new Chef();
            chef.setUserName("Amber");
            chef.setPassword("foodlove11");
            chefs.save(chef);
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model, String name, String type) {
        String userName = (String) session.getAttribute("userName");
        Chef chef = chefs.findFirstByuserName(userName);
        if (chef != null) {
            model.addAttribute("chef", chef);

        }
        List<Recipe> recipeList = recipes.findAll();
        if (type != null) {
        }
        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password) throws Exception {
        Chef chef = chefs.findFirstByuserName(userName);
        if (chef == null) {
            chefs.save(new Chef(userName, password));
        } else if (!chef.verifyPassword(password)) {
            throw new Exception("Login Failed");
        }
        session.setAttribute("userName", userName);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(path = "/add-recipe", method = RequestMethod.POST)
    public String createRecipe(HttpSession session, String name, String type, int temp, String chef) {
        String userName = (String) session.getAttribute("userName");
        if (userName != null) {
            Recipe recipe = new Recipe(name, type, temp, chef);
            recipes.save(recipe);
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/modify", method = RequestMethod.POST)
    public String updateRecipe(HttpSession session, String name, String type, int temp, String chef) {
        String userName = (String) session.getAttribute("userName");
        if (userName != null) {
            Recipe recipe = new Recipe(name, type, temp, chef);
            recipes.save(recipe);
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/delete-message", method = RequestMethod.POST)
    public String deleteMessage(HttpSession session, Integer id) {
        recipes.delete(id - 1);
        return "redirect:/";
    }

}