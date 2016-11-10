package com.github.yaroslavguschak.DAO;

import com.github.yaroslavguschak.entity.Contact;
import com.github.yaroslavguschak.entity.User;
import com.github.yaroslavguschak.entityrequest.ContactRequest;
import com.github.yaroslavguschak.entityrequest.SearchRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ygusc on 07.11.2016.
 */
public interface ContactDAO {
    @Transactional(readOnly=true)
    List<Contact> findAllContacts();

    List<Contact> findContactsByAllFieds(SearchRequest searchRequest, User user);

    @Transactional(readOnly=true)
    List<Contact> findContactsByUserId(int id);

    @Transactional(readOnly=true)
    Contact findContactById(int id);

    void updateContact(ContactRequest contact, User user);

    void deleteContactById(Integer contactId, User user);

    Contact createContact(Contact contact, User user);
}
