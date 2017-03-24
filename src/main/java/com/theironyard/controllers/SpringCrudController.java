package com.theironyard.controllers;

import com.theironyard.entities.Contact;
import com.theironyard.entities.User;
import com.theironyard.services.ContactRepository;
import com.theironyard.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SpringCrudController {
    @Autowired
    UserRepository users;
    @Autowired
    ContactRepository contacts;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName != null) {
            User user = users.findFirstByName(userName);
            model.addAttribute("user", user);
            List<Contact> usersContacts = contacts.findAllByUser(user);
            model.addAttribute("usersContacts", usersContacts);
        }

        List<Contact> listOfContacts = contacts.findAll();
        if (listOfContacts != null) {
            model.addAttribute("listOfContacts", listOfContacts);
        }

        Integer editId = (Integer)session.getAttribute("editId");
        if (editId != null) {
            model.addAttribute("editId", editId);
        }

        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password) throws Exception {
        User user = users.findFirstByName(userName);
        if (user == null) {
            users.save(new User(userName, password));
        } else if (!user.verifyPassword(password)) {
            throw new Exception("Login failed");
        }
        session.setAttribute("userName", userName);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(path = "/create-contact", method = RequestMethod.POST)
    public String createContact(HttpSession session, String contactName, String email, String phone) {
        String userName = (String) session.getAttribute("userName");
        User user = users.findFirstByName(userName);
        Contact contact = new Contact(contactName, email, phone, user);
        contacts.save(contact);
        return "redirect:/";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public String delete(Integer deleteId) {
        contacts.delete(deleteId);
        return "redirect:/";
    }

    @RequestMapping(path = "/edit", method = RequestMethod.POST)
    public String edit(HttpSession session, Integer editId) {
        session.setAttribute("editId", editId);
        return "redirect:/";
    }

    @RequestMapping(path = "/edit-contact", method = RequestMethod.POST)
    public String editContact(HttpSession session, String contactName, String phone, String email) {
        Integer editId = (Integer) session.getAttribute("editId");
        session.removeAttribute("editId");
        String userName = (String) session.getAttribute("userName");
        User user = users.findFirstByName(userName);
        Contact contact = new Contact(editId, contactName, email, phone, user);
        contacts.save(contact);
        return "redirect:/";
    }
}