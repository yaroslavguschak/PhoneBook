package com.github.yaroslavguschak.entity;

import com.github.yaroslavguschak.util.CSHA1Util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {

    private Integer id;
    private String login;
    private String fullName;
    private String passwordhash;
    @XmlElement(name = "contact")
    private List<Contact> contacts = new ArrayList<>();

    public User() {
        this.login = "Anonymous";
        this.passwordhash = "Anonymous";
        this.fullName = "Anonymous";
    }

    public User(String login, String fullName, String password) throws GeneralSecurityException {
        this.login = login;
        this.fullName = fullName;
        this.passwordhash = CSHA1Util.getSHA1String(password);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordhash() {
        return passwordhash;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    public void setPassword(String password) throws GeneralSecurityException {
        this.passwordhash = CSHA1Util.getSHA1String(password);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", fullName='" + fullName + '\'' +
                ", passwordhash='" + passwordhash + '\'' +
                ", contacts=" + contacts +
                '}';
    }
}
