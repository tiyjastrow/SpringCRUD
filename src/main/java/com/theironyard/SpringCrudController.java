package com.theironyard;

import com.theironyard.services.PeriodicElementRepository;
import com.theironyard.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Zach on 10/10/16.
 */
@Controller
public class SpringCrudController {

    @Autowired
    UserRepository users;

    @Autowired
    PeriodicElementRepository periodicElements;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model) {
        String userName = (String) session.getAttribute("userName");
        List<PeriodicElement> elementList = periodicElements.findAllByOrderById();

        getCurrentUser(model, userName);

        validateAuthor(userName, elementList);

        model.addAttribute("periodicElements", elementList);
        return "home";
    }

    private void getCurrentUser(Model model, String userName) {
        if (userName != null) {
            User user = users.findFirstByName(userName);
            model.addAttribute("user", user);
        }
    }

    private void validateAuthor(String userName, List<PeriodicElement> elementList) {
        for (PeriodicElement p : elementList) {
            if (p.user.name.equalsIgnoreCase(userName)) {
                p.author = "notNull";
            }
        }
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String name, String password) throws Exception {
        User user = users.findFirstByName(name);
        if (user == null) {
            user = new User(name, PasswordStorage.createHash(password));
            users.save(user);
        } else if (!PasswordStorage.verifyPassword(password, user.getPassword())) {
            throw new Exception("Invalid Password");
        }
        session.setAttribute("userName", name);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(path = "/add-element", method = RequestMethod.POST)
    public String addElement(HttpSession session, String elementName, String elementSymbol, int atomicNumber, float atomicWeight) {
        String userName = (String) session.getAttribute("userName");
        User user = users.findFirstByName(userName);

        PeriodicElement newElement = new PeriodicElement(elementName, elementSymbol, atomicNumber, atomicWeight, user);
        periodicElements.save(newElement);
        return "redirect:/";
    }

    @RequestMapping(path = "edit-element", method = RequestMethod.GET)
    public String editElement(Model model, Integer id) {
        List<PeriodicElement> elementList = periodicElements.findAllByOrderById();
        PeriodicElement element = new PeriodicElement();
        for (PeriodicElement p : elementList) {
            if (p.id == id) {
                element = p;
            }
        }
        model.addAttribute("elementToEdit", element);
        return "update";
    }

    @RequestMapping(path = "/edit-element", method = RequestMethod.POST)
    public String update(String elementName, String elementSymbol, int atomicNumber, float atomicWeight, Integer id) {
        PeriodicElement editElement = periodicElements.findOne(id);
        editElement.name = elementName;
        editElement.symbol = elementSymbol;
        editElement.atomicNumber = atomicNumber;
        editElement.atomicWeight = atomicWeight;

        periodicElements.save(editElement);
        return "redirect:/";
    }

    @RequestMapping(path = "/delete-element", method = RequestMethod.POST)
    public String delete(Integer id) {
        periodicElements.delete(id);
        return "redirect:/";
    }
}
