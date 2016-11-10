package com.github.yaroslavguschak.DAO.xml;

import com.github.yaroslavguschak.DAO.ContactDAO;
import com.github.yaroslavguschak.entity.Contact;
import com.github.yaroslavguschak.entity.User;
import com.github.yaroslavguschak.entityrequest.ContactRequest;
import com.github.yaroslavguschak.entityrequest.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Profile("xml")
@Component
public class ContactDAOXML implements ContactDAO {

    @Autowired
    XMLdb xmLdb;

    @Autowired
    XMLConverter xmlConverter;

    @Override
    public List<Contact> findAllContacts() {
        return null;
    }

    @Override
    public List<Contact> findContactsByAllFieds(SearchRequest searchRequest, User user) {
        List<Contact> searchResult = new ArrayList<>();
        for (Contact c : user.getContacts()) {
            if (c.getFirstName().toLowerCase().contains(searchRequest.getSearchTextInput().toLowerCase())||
                    c.getLastName().toLowerCase().contains(searchRequest.getSearchTextInput().toLowerCase())||
                    c.getPatronymicName().toLowerCase().contains(searchRequest.getSearchTextInput().toLowerCase())||
                    c.getCellPhone().contains(searchRequest.getSearchTextInput().toLowerCase())||
                    c.getHomeNumber().toLowerCase().contains(searchRequest.getSearchTextInput().toLowerCase())||
                    c.getAddress().toLowerCase().contains(searchRequest.getSearchTextInput().toLowerCase())||
                    c.getEmail().toLowerCase().contains(searchRequest.getSearchTextInput().toLowerCase()))
            {searchResult.add(c);
            }
        }

        return searchResult;
    }

    @Override
    public List<Contact> findContactsByUserId(int id) {
        List<Contact> userContacts = new ArrayList<>();

        for (User u:xmLdb.getUserList()) {
            if(u.getId().equals(id)){
                return u.getContacts();
            }
        }
        return userContacts;
    }

    @Override
    public Contact findContactById(int id) {
        Contact contact = null;
        for (User u:xmLdb.getUserList()) {
            for (Contact c:u.getContacts()) {
                if (c.getId().equals(id)){
                    return c;
                }
            }
        }
        return contact;
    }

    @Override
    public void updateContact(ContactRequest contactRequest, User user) {
        Contact contact = findContactById(contactRequest.getId());
        contact.setFirstName(contactRequest.getFirstName());
        contact.setLastName(contactRequest.getLastName());
        contact.setPatronymicName(contactRequest.getPatronymicName());
        contact.setAddress(contactRequest.getAddress());
        contact.setCellPhone(contactRequest.getCellPhone());
        contact.setHomeNumber(contactRequest.getHomeNumber());
        contact.setEmail(contactRequest.getEmail());
        try {
            xmlConverter.marshalingXMLDB(xmLdb);
        } catch (JAXBException e){
            System.out.println(e);
        }
    }

    @Override
    public void deleteContactById(Integer contactId, User user) {
        List<Contact> contacts = user.getContacts();
        Iterator<Contact> iter = contacts.iterator();
        while(iter.hasNext()){
            Contact next = iter.next();
            if(next.getId().equals(contactId))
                iter.remove();
        }
        try {
            xmlConverter.marshalingXMLDB(xmLdb);
        } catch (JAXBException e){
            System.out.println(e);
        }
    }

    @Override
    public Contact createContact(Contact contact, User user) {
        int max = 0;
        for (User u:xmLdb.getUserList()) {
            for (Contact c:u.getContacts()) {
                if (c.getId() > max){
                    max = c.getId();
                }
            }
        }
        ++max;
        contact.setId(max);
        user.getContacts().add(contact);
        try {
            xmlConverter.marshalingXMLDB(xmLdb);
        } catch (JAXBException e){
            System.out.println(e);
        }

        return contact;
    }
}
