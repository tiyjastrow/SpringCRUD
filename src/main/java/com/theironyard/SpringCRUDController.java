package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by jeremypitt on 10/10/16.
 */
@Controller
public class SpringCRUDController {
    @Autowired
    ConventionRepository conventions;

    @Autowired
    UserRepository users;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session){
        String userName = (String) session.getAttribute("userName");
        List<Convention> conList = conventions.findAllByOrderById();
        List<Convention> userConList = new ArrayList<>();
        if (userName != null) {
            User user = users.findFirstByName(userName);
            model.addAttribute("user", user);
            userConList = conventions.findByUser(user);
        }
        conList.removeAll(userConList);
        model.addAttribute("userConList", userConList);
        model.addAttribute("conventions", conList);
        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String name, String password) throws Exception {
        User user = users.findFirstByName(name);
        if (user == null) {
            user = new User(name, PasswordStorage.createHash(password));
            users.save(user);
        }
        else if (! PasswordStorage.verifyPassword(password, user.getPassword())){
            throw new Exception("Invalid password");
        }
        session.setAttribute("userName", name);

        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session){
        session.invalidate();

        return "redirect:/";
    }

    @RequestMapping(path = "/add-convention", method = RequestMethod.POST)
    public String addConvention(HttpSession session, String conName, String conLocation, int conPop, String conGenre) {
        String userName = (String) session.getAttribute("userName");
        User user = users.findFirstByName(userName);
        Convention convention = new Convention(conName, conLocation, conPop, conGenre, user);
        conventions.save(convention);

        return "redirect:/";
    }

    @RequestMapping(path = "/update", method = RequestMethod.GET)
    public String updateConvention(Model model, int id) {
        List<Convention> conventionList = conventions.findAllByOrderById();
        Convention convention = new Convention();
        for (Convention c: conventionList)
            if (c.id == id){
                convention = c;
            }
        model.addAttribute("conventionToEdit", convention);
        return "update";
    }

    @RequestMapping(path = "/update-convention", method = RequestMethod.POST)
    public String updateCon(String conName, String conLocation, int conPop, String conGenre, int id){
        Convention editMe = conventions.findOne(id);
        editMe.name = conName;
        editMe.location = conLocation;
        editMe.maxPop = conPop;
        editMe.category = conGenre;
        conventions.save(editMe);

        return "redirect:/";
    }

    @RequestMapping(path = "/delete-convention", method = RequestMethod.POST)
    public String deleteConvention(int id){
        Convention convention = conventions.findOne(id);
        conventions.delete(convention);
        return "redirect:/";
    }
}
