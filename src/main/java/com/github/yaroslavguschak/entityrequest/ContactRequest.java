package com.github.yaroslavguschak.entityrequest;


import com.github.yaroslavguschak.entity.Contact;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ContactRequest {

    private Integer id;

    @NotNull
    @Size(min = 4, message = "min 4 chars in your first name")
    private String firstName;

    @NotNull
    @Size(min = 4, message = "min 4 chars in your last name")
    private String lastName;

    @NotNull
    @Size(min = 4, message = "min 4 chars in your patronymic name")
    private String patronymicName;

    @NotNull (message = "please, input your cell phone")
    @Pattern(regexp = "^\\+\\d{3}\\(\\d{2}\\)\\d{7}$",
            message = "please use template +380(66)1234567")
    private String cellPhone;

    @Pattern(regexp = "^\\+\\d{3}\\(\\d{2}\\)\\d{7}$",
            message = "please use template +380(66)1234567")
    private String homeNumber;

    private String address;

    @Pattern(regexp = "^(?:[a-zA-Z0-9_'^&/+-])+(?:\\.(?:[a-zA-Z0-9_'^&/+-])+)" +
            "*@(?:(?:\\[?(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))\\.)" +
            "{3}(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\]?)|(?:[a-zA-Z0-9-]+\\.)" +
            "+(?:[a-zA-Z]){2,}\\.?)$",
            message = "Email is incorrect!")
    private String email;

    public ContactRequest() {
    }

    public ContactRequest(Integer id, String firstName, String lastName, String patronymicName,
                          String cellPhone, String homeNumber, String address, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymicName = patronymicName;
        this.cellPhone = cellPhone;
        this.homeNumber = homeNumber;
        this.address = address;
        this.email = email;
    }

    public ContactRequest(Contact contact) {
        this.id = contact.getId();
        this.firstName = contact.getFirstName();
        this.lastName = contact.getLastName();
        this.patronymicName = contact.getPatronymicName();
        this.cellPhone = contact.getCellPhone();
        this.homeNumber = contact.getHomeNumber();
        this.address = contact.getAddress();
        this.email = contact.getEmail();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymicName() {
        return patronymicName;
    }

    public void setPatronymicName(String patronymicName) {
        this.patronymicName = patronymicName;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
