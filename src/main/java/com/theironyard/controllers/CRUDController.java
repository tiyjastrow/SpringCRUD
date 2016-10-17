package com.theironyard.controllers;

import com.theironyard.entities.Job;
import com.theironyard.entities.User;
import com.theironyard.services.JobRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joshuakeough on 10/11/16.
 */
@Controller
public class CRUDController {
    @Autowired
    UserRepository users;
    @Autowired
    JobRepository jobs;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password, String job) throws Exception {
        User user = users.findFirstByUserName(userName);
        if (user == null) {
            user = new User(userName, PasswordStorage.createHash(password), job);
            users.save(user);
        } else if (!PasswordStorage.verifyPassword(password, user.getPassword())) {
            return "redirect:/";
        }
        session.setAttribute("userName", userName);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model, Integer salary, String position, Integer yearsOfExperienceNeeded, String cityLocated,String byUser) {
        String userName = (String) session.getAttribute("userName");
        User user = users.findFirstByUserName(userName);

        ArrayList<Job> jobList;
        if(salary != null){
            jobList = (ArrayList)jobs.findBySalaryGreaterThan(salary);
        }else if(position != null){
            jobList = (ArrayList)jobs.findByPosition(position);
        }else if(yearsOfExperienceNeeded !=null) {
            jobList = (ArrayList)jobs.findByYearsOfExperienceNeededLessThan(yearsOfExperienceNeeded);
        }else if(cityLocated!= null) {
            jobList = (ArrayList)jobs.findByCityLocated(cityLocated);
        }else if(byUser != null) {
            jobList = (ArrayList)jobs.findByUser(user);
        } else {
            jobList = (ArrayList<Job>) jobs.findAll();
        }
        for (Job j : jobList) {
            if (user == j.getUser()) {
                j.setShow("show");
            }
        }




        model.addAttribute("jobs", jobList);
        model.addAttribute("user", user);

        return "home";
    }

    @RequestMapping(path = "/add-job", method = RequestMethod.POST)
    public String addJob(HttpSession session, String job, String location, Integer experience, Integer salary) {
        String userName = (String) session.getAttribute("userName");
        User user = users.findFirstByUserName(userName);
        Job newjob = new Job(salary, job, experience, location, user, null);
        jobs.save(newjob);
        return "redirect:/";
    }

    @RequestMapping(path = "/edit-job", method = RequestMethod.POST)
    public String editJob(HttpSession session, String job, String location, Integer experience, Integer salary, Integer id) {
        String userName = (String) session.getAttribute("userName");
        User user = users.findFirstByUserName(userName);
        Job newjob = new Job();
        newjob.setId(id);
        newjob.setPosition(job);
        newjob.setCityLocated(location);
        newjob.setYearsOfExperienceNeeded(experience);
        newjob.setSalary(salary);
        newjob.setShow(null);
        newjob.setUser(user);
        jobs.save(newjob);
        return "redirect:/";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public String delete(Integer id) {
        jobs.delete(id);
        return "redirect:/";
    }

    @RequestMapping(path = "/job", method = RequestMethod.GET)
    public String job(HttpSession session, Model model, Integer id) {
        String userName = (String) session.getAttribute("userName");
        User user = users.findFirstByUserName(userName);
        if (user == null) {
            return "home";
        }
        Job job = jobs.findOne(id);
        if(user.getUserName().equals(job.getUser().getUserName())) {
            job.setShow("show");
        }
        model.addAttribute("job", job);

        return "job";
    }


}
