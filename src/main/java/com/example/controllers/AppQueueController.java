/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controllers;

import com.example.senders.MessageQueueSender;
import com.example.models.User;
import com.example.models.UserDao;
import javax.validation.Valid;
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
public class AppQueueController {

    static final Logger log = LoggerFactory.getLogger(AppQueueController.class);

    @Autowired
    MessageQueueSender messageQueueSender;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/queue", method = RequestMethod.GET)
    public String prepareOrder(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user";
    }

    @RequestMapping(value = {"/queue"}, method = RequestMethod.POST)
    public String sendOrder(@Valid User user, BindingResult result,
            ModelMap model) {
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
        log.info("Application : sending order request {}", user);
        messageQueueSender.sendMessage(user);
    }
}
