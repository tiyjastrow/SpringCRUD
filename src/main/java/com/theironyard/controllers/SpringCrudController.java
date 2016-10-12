package com.theironyard.controllers;

import com.theironyard.entities.*;
import com.theironyard.services.*;
import com.theironyard.utilities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by joe on 11/10/2016.
 */

@Controller
public class SpringCrudController {

    @Autowired
    PlayerRepository players;

    @Autowired
    InformationRepository player_information;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String name, String password) throws Exception {

        Player player = players.findByUserName(name);

        if (player == null) {
            Player p = new Player(name, PasswordStorage.createHash(password));
            players.save(p);
        }

        else if (! PasswordStorage.verifyPassword (password, player.getPassword())) {
            return "redirect:/";
        }

        session.setAttribute("name", name);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model) {

        String name = (String) session.getAttribute("name");

        Player p = players.findByUserName(name);

        ArrayList<Information> information = (ArrayList<Information>) player_information.findAll();

        if (name != null) {
            for (Information i : information) {
                Information check = player_information.findOne(i.getId());
                if (p.getId() == i.getPlayer().getId()){
                    check.setDisplay("yes");
                    player_information.save(check);
                }
                else {
                    check.setDisplay(null);
                    player_information.save(check);
                }
            }
        }

        Collections.sort(information);

        model.addAttribute("player", p);
        model.addAttribute("information", information);
        return "home";
    }

    @RequestMapping(path = "/add-entry", method = RequestMethod.POST)
    public String addEntry(HttpSession session, String firstName, String lastName, String country, String playingStyle, int wins, int losses) {

        String name = (String) session.getAttribute("name");
        String display = "yes";
        Player player = players.findByUserName(name);

        player_information.save(new Information(firstName, lastName, country, playingStyle, wins, losses, player, display));

        return "redirect:/";
    }

    @RequestMapping(path = "/edit-entry", method = RequestMethod.POST)
    public String editEntry(String firstName, String lastName, String country, String playingStyle, String wins, String losses, int id) {

        Information info = player_information.findOne(id);

        if (!firstName.isEmpty() && firstName != null) {
            info.setFirst_name(firstName);
        }
        if (!lastName.isEmpty() && lastName != null) {
            info.setLast_name(lastName);
        }
        if (!country.isEmpty() && country != null) {
            info.setCountry(country);
        }
        if (!playingStyle.isEmpty() && playingStyle != null) {
            info.setPlaying_style(playingStyle);
        }
        if (!wins.isEmpty() && wins != null) {
            int w = Integer.parseInt(wins);
            info.setWins(w);
        }
        if (!losses.isEmpty() && losses != null) {
            int loss = Integer.parseInt(losses);
            info.setLosses(loss);
        }

        player_information.save(info);
        return "redirect:/";
    }

    @RequestMapping(path = "/delete-entry", method = RequestMethod.POST)
    public String deleteEntry(int id) {
        player_information.delete(id);
        return "redirect:/";
    }
}
