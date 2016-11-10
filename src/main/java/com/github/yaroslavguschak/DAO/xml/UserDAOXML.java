package com.github.yaroslavguschak.DAO.xml;

import com.github.yaroslavguschak.DAO.UserDAO;
import com.github.yaroslavguschak.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.util.List;

@Profile("xml")
@Component
public class UserDAOXML implements UserDAO {
    @Autowired
    XMLdb xmLdb;

    @Autowired
    XMLConverter xmlConverter;



    @Override
    public List<User> findAllUsers() {

        return xmLdb.getUserList();
    }

    @Override
    public User findUserById(int id) {
        return null;
    }

    @Override
    public User findUserByLogin(String login) {
        User user = null;
        for(User u: xmLdb.getUserList()){
            if(u.getLogin().equals(login)){
                user = u;
            }
        }
        return user;
    }

    @Override
    public String getPassHashByLogin(String login) {
        String passHash = null;
        for(User u: xmLdb.getUserList()){
            if(u.getLogin().equals(login)){
                passHash = u.getPasswordhash();
            }
        }
        return passHash;
    }

    @Override
    public Integer getUserCountByLogin(String login) {
        return null;
    }

    @Override
    public boolean isUserExistByLogin(String userLogin) {
        xmLdb.setUserList(xmlConverter.unmarshalingXMLDB().getUserList());
        for(User user: xmLdb.getUserList()){
            if(user.getLogin().equals(userLogin))
                return true;
        }
        return false;
    }

    @Override
    public User createUser(User user){
        System.out.println("creating user in XML DB with login " + user.getLogin());

        int newUserId = xmLdb.getUserList().size() + 1;
        user.setId(newUserId);
        xmLdb.getUserList().add(user);
        try {
            xmlConverter.marshalingXMLDB(xmLdb);
        } catch (JAXBException e){
            System.out.println(e);
        }

        return user;
    }
}
