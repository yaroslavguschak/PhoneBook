package com.github.yaroslavguschak.controllers;

import com.github.yaroslavguschak.DAO.ContactDAO;
import com.github.yaroslavguschak.DAO.UserDAO;
import com.github.yaroslavguschak.entity.Contact;
import com.github.yaroslavguschak.entity.UserSessionWrapper;

import com.github.yaroslavguschak.entityrequest.LoginRequest;
import com.github.yaroslavguschak.entityrequest.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    ContactDAO contactDAO;

    @Autowired
    UserSessionWrapper userWrapper;

    @RequestMapping(value = "/search")
    public ModelAndView showIndex(@ModelAttribute("searchRequest") SearchRequest searchRequest)  {
        final ModelAndView mav = new ModelAndView("/search");

        String matchesCount;

        if(userWrapper.getUser() !=null){
            mav.addObject("showuser", userWrapper.getUser());
            List <Contact> searchedResult = contactDAO.findContactsByAllFieds(searchRequest, userWrapper.getUser());
            mav.addObject("searchedResultList", searchedResult);
            mav.addObject("searchRequest", searchRequest);
            matchesCount = "We have " + searchedResult.size() + " matches";
        } else {
            mav.addObject("loginRequest", new LoginRequest());
            matchesCount = "Please, login and use search ;)";
        }

        mav.addObject("matchesCount", matchesCount);
        return mav;
    }
}
