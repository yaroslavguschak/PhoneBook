package com.github.yaroslavguschak.DAO.xml;
import com.github.yaroslavguschak.entity.Contact;
import com.github.yaroslavguschak.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("xml")
@XmlRootElement(name = "xmldb")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLdb {
    @XmlElement(name = "user")
    private List<User> userList = new ArrayList<>();

    @XmlTransient
    @Autowired
    XMLConverter xmlConverter;

    public XMLdb() {
    }

    public XMLdb(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }


    @Override
    public String toString() {
        return "XMLdb{" +
//                "userList=" + userList.toString() +
                '}';
    }
}
