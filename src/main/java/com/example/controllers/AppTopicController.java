package com.example.controllers;

import com.example.senders.MessageTopicSender;
import com.example.models.Topic;
import javax.validation.Valid;

import com.example.models.User;
import com.example.models.UserDao;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class AppTopicController {

    static final Logger log = LoggerFactory.getLogger(AppTopicController.class);

    @Autowired
    private MessageTopicSender messageTopicSender;
    
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/topic", method = RequestMethod.GET)
    public String prepareOrder(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("topics", Arrays.asList(Topic.values()));
        return "user";
    }

    @RequestMapping(value = {"/topic"}, method = RequestMethod.POST)
    public String sendOrder(@Valid User user, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "user";
        }
        userDao.create(user);
        sendOrder(user);
        model.addAttribute("success", "User with name " + user.getName() + " registered.");
        return "ordersuccess";
    }

    @Transactional
    public void sendOrder(User user) {
        log.info("Application : sending user request {}", user);
        messageTopicSender.sendMessage(user);
    }
}
