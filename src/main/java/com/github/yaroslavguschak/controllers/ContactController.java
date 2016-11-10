package com.github.yaroslavguschak.controllers;

import com.github.yaroslavguschak.DAO.ContactDAO;
import com.github.yaroslavguschak.DAO.UserDAO;
import com.github.yaroslavguschak.entity.Contact;
import com.github.yaroslavguschak.entity.UserSessionWrapper;
import com.github.yaroslavguschak.entityrequest.ContactRequest;
import com.github.yaroslavguschak.entityrequest.LoginRequest;
import com.github.yaroslavguschak.entityrequest.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;


@Controller
public class ContactController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    ContactDAO contactDAO;

    @Autowired
    UserSessionWrapper userWrapper;



    @RequestMapping(value = "/newcontact")
    public ModelAndView addContact() {
        final ModelAndView mav = new ModelAndView("/newcontact");

        if(userWrapper.getUser() !=null){
            mav.addObject("showuser", userWrapper.getUser());
            mav.addObject("searchRequest", new SearchRequest());
            mav.addObject("contactRequest", new ContactRequest());
        } else {
            mav.addObject("loginRequest", new LoginRequest());
        }
        return mav;
    }

    @RequestMapping(value = "/doaddcontact", method = RequestMethod.POST)
    public ModelAndView addBook(@Valid @ModelAttribute("contactRequest") ContactRequest contactRequest,
                                BindingResult bindingResult ) {
        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("newcontact");
            mav.addObject("showuser", userWrapper.getUser());
            mav.addObject("searchRequest", new SearchRequest());
            return mav;
        } else {
            Contact contact = new Contact();
            contact.setFirstName(contactRequest.getFirstName());
            contact.setLastName(contactRequest.getLastName());
            contact.setPatronymicName(contactRequest.getPatronymicName());
            contact.setAddress(contactRequest.getAddress());
            contact.setCellPhone(contactRequest.getCellPhone());
            contact.setHomeNumber(contactRequest.getHomeNumber());
            contact.setEmail(contactRequest.getEmail());

            contactDAO.createContact(contact, userWrapper.getUser());
            return new ModelAndView("redirect:/phonebook");
        }
    }


    @RequestMapping(value = "/saveeditcontact", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView saveEditContact(@Valid @ModelAttribute("contactRequest") ContactRequest contactRequest,
                                        BindingResult bindingResult) throws ServletException {

        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("edit");
            mav.addObject("showuser", userWrapper.getUser());
            mav.addObject("searchRequest", new SearchRequest());
            return mav;
        } else {
            contactDAO.updateContact(contactRequest, userWrapper.getUser());
            return new ModelAndView("redirect:/phonebook");
            }

    }


    @RequestMapping(value = "/editcontact" , method= RequestMethod.POST)
    public ModelAndView editContactForm( HttpServletRequest req) throws ServletException, IOException, GeneralSecurityException {

        if(userWrapper.getUser() !=null){
            Integer   contactId = Integer.valueOf(req.getParameter("contactId"));

            Contact contact = contactDAO.findContactById(contactId);

                System.out.print("LOG: " + "user " + userWrapper.getUser().getLogin()+ " editing contact # "
                        + contactId + " " + contact.getLastName() );
                ContactRequest contactRequest = new ContactRequest(contact);
                final ModelAndView mav = new ModelAndView("edit");
                mav.addObject("searchRequest", new SearchRequest());
                mav.addObject("showuser", userWrapper.getUser());
                mav.addObject("contactRequest", contactRequest);
                return mav;
        }
        return new ModelAndView("redirect:/phonebook");
    }


    @RequestMapping(value = "/deletecontact" , method= RequestMethod.POST)
    public ModelAndView deleteContact( HttpServletRequest req) throws ServletException, IOException, GeneralSecurityException {

        if(userWrapper.getUser() !=null){
            Integer   contactId = Integer.valueOf(req.getParameter("contactId"));

                System.out.println("LOG: " + "user " + userWrapper.getUser().getLogin()+ " delete # " +
                        contactId);
                ;
                contactDAO.deleteContactById(contactId, userWrapper.getUser());
            }
        return new ModelAndView("redirect:/phonebook");
    }







}