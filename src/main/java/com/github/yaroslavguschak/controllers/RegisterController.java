package com.github.yaroslavguschak.controllers;

import com.github.yaroslavguschak.DAO.UserDAO;
import com.github.yaroslavguschak.entity.User;
import com.github.yaroslavguschak.entity.UserSessionWrapper;
import com.github.yaroslavguschak.entityrequest.LoginRequest;
import com.github.yaroslavguschak.entityrequest.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.GeneralSecurityException;


@Controller
@SessionAttributes(types = LoginRequest.class)
public class RegisterController {
    @Autowired
    UserDAO userDAO;

    @Autowired
    UserSessionWrapper userWrapper;


    @RequestMapping(value = "/register")
    public ModelAndView registerGet() {
        ModelAndView mav = new ModelAndView("/register");
        mav.addObject("registerRequest",new RegisterRequest());
        mav.addObject("loginRequest",new LoginRequest());
        return mav;
    }


    @RequestMapping(value = "/doregister", method= RequestMethod.POST)
    public ModelAndView doRegister(@Valid @ModelAttribute("registerRequest") RegisterRequest regReq,
                           BindingResult bindingResult) throws GeneralSecurityException {
        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("register");
            mav.addObject("loginRequest",new LoginRequest());
            return mav;
        } else {
            User user = new User(regReq.getLogin(), regReq.getFullName(), regReq.getPassword());
            user = userDAO.createUser(user);
            userWrapper.setUser(user);
            ModelAndView mav = new ModelAndView("redirect:/phonebook");
            return mav;
        }
    }


}