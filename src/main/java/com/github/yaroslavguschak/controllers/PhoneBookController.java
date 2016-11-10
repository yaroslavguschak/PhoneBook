package com.github.yaroslavguschak.controllers;

import com.github.yaroslavguschak.entity.UserSessionWrapper;
import com.github.yaroslavguschak.entityrequest.LoginRequest;
import com.github.yaroslavguschak.entityrequest.SearchRequest;
import com.github.yaroslavguschak.util.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;




@Controller
public class PhoneBookController {

    @Autowired
    UserSessionWrapper userWrapper;


    @RequestMapping(value = "/")
    public String rootRedirect()  {
        return "redirect:/phonebook";
    }

    @RequestMapping(value = "/phonebook")
    public ModelAndView showPhoneBook()  {
        final ModelAndView mav = new ModelAndView("/phonebook");

        if (userWrapper.getAlert().getIsShow()){
            mav.addObject("message", userWrapper.getAlert().getMessage());
            System.out.println(userWrapper.getAlert().getMessage());
            userWrapper.getAlert().setShow(false);
        }

        if (userWrapper.getUser() != null) {
            mav.addObject("showuser", userWrapper.getUser());
            mav.addObject("searchRequest", new SearchRequest());
        } else {
            mav.addObject("loginRequest", new LoginRequest());
            mav.addObject("message", userWrapper.getAlert().getMessage());
        }
        return mav;

    }

}
