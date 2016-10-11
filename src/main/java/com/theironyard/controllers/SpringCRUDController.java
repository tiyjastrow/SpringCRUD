package com.theironyard.controllers;

import com.theironyard.entities.Concert;
import com.theironyard.entities.User;
import com.theironyard.services.ConcertRepository;
import com.theironyard.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by jakefroeb on 10/10/16.
 */
@Controller
public class SpringCRUDController {
    @Autowired
    ConcertRepository concerts;
    @Autowired
    UserRepository users;

    @RequestMapping(path="/", method = RequestMethod.GET)
    String home(HttpSession session, Model model){
        String userName = (String) session.getAttribute("userName");
        List<Concert> concertEntities = concerts.findAll();
        if(userName != null){
            User user = users.findFirstByName(userName);
            model.addAttribute("user", user);
            for (Concert concert :concertEntities) {
                if(concert.user.getName().equals(userName)){
                    concert.setSameUser("not Null");
                }
                else {
                    concert.setSameUser(null);
                }
            }
        }
        model.addAttribute("concerts", concertEntities);
        return "home";
    }
    @RequestMapping(path = "/editMessage", method = RequestMethod.GET)
    String editMessage(HttpSession session, Model model){
        int id = (int) session.getAttribute("id");
        Concert concert = concerts.findFirstById(id);
        model.addAttribute("concert", concert);
        return "editMessage";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String name, String password) throws Exception {
        User user = users.findFirstByName(name);
        if (user == null) {
            user = new User(name, password);
            users.save(user);
        } else if (!user.isValid(password)){
            throw new Exception("Invalid Password");
        }
        session.setAttribute("userName", name);

        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(path = "/create-concert", method = RequestMethod.POST)
    public String createConcert(HttpSession session, String band, String venue, String year){
        String userName = (String) session.getAttribute("userName");
        User user = users.findFirstByName(userName);
        Concert concert = new Concert(band,venue,Integer.parseInt(year),user);
        concerts.save(concert);
        return "redirect:/";
    }
    @RequestMapping(path = "/delete-concert", method = RequestMethod.POST)
    public String deleteConcert(int id){
        concerts.delete(id);
        return "redirect:/";
    }
    @RequestMapping( path = "/edit-concert", method = RequestMethod.POST)
    public String editConcert(HttpSession session, int id){
        session.setAttribute("id", id);
        return "redirect:/editMessage";
    }
    @RequestMapping( path="/edit", method = RequestMethod.POST)
    String edit(HttpSession session, String band, String venue, String year){
        int id = (int) session.getAttribute("id");
        Concert concert = concerts.findFirstById(id);
        if(band != null && !band.equals("")){
            concert.setBand(band);
        }
        if(venue != null && !venue.equals("")){
            concert.setVenue(venue);
        }
        if(year != null && !year.equals("")){
            concert.setYear(Integer.parseInt(year));
        }
        concerts.save(concert);
        return "redirect:/";
    }
}
