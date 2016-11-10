package com.github.yaroslavguschak.controllers;


import com.github.yaroslavguschak.DAO.UserDAO;
import com.github.yaroslavguschak.DAO.xml.XMLConverter;
import com.github.yaroslavguschak.DAO.xml.XMLdb;
import com.github.yaroslavguschak.entity.Contact;
import com.github.yaroslavguschak.entity.UserSessionWrapper;
import com.github.yaroslavguschak.entityrequest.LoginRequest;
import com.github.yaroslavguschak.util.CSHA1Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.security.GeneralSecurityException;


@Controller
public class LoginController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    UserSessionWrapper userWrapper;

    @Autowired
    private Environment env;

//    @Autowired
//    XMLConverter xmlConverter;
//
//    @Autowired
//    XMLdb xmLdb;



    @RequestMapping(value = "/loginchek", method = RequestMethod.POST)
    public ModelAndView loginCheck(@ModelAttribute("loginRequest") LoginRequest loginRequest ) throws GeneralSecurityException, IOException, JAXBException {

        String login = loginRequest.getLogin();

        if (userDAO.isUserExistByLogin(login)){
            String passwordhashFromRequest = CSHA1Util.getSHA1String(loginRequest.getPassword());

            if (passwordhashFromRequest.equals(userDAO.getPassHashByLogin(login))){
                userWrapper.setUser(userDAO.findUserByLogin(login));
            } else {
                userWrapper.getAlert().setMessage("pass for login [" + login + "]  is incorrect");
                userWrapper.getAlert().setShow(true);
            }
        } else {
            userWrapper.getAlert().setMessage("User with login [" + login + "] not exist");
            userWrapper.getAlert().setShow(true);
        }
        return new ModelAndView("redirect:/phonebook");
    }


    @RequestMapping(value = "/logout")
    public ModelAndView logOut() {
        final ModelAndView mav = new ModelAndView("redirect:/phonebook" );

        userWrapper.setUser(null);
        LoginRequest loginRequest = new LoginRequest();
        mav.addObject("loginRequest",loginRequest);
        return mav;
    }








}