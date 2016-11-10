package com.github.yaroslavguschak.entityrequest;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterRequest {

    @Size(min = 3, message = "min 3 chars in your login")
    @Pattern(regexp = "[a-zA-Z0-9_-]+",
            message = "use alphabetic chars for login")
    private String login        = "";

    @Size(min = 5, message = "min 5 chars in your full name")
    private String fullName     = "";

    @Size(min = 5, message = "min 5 chars in your full name")
    private String password     = "";

    public RegisterRequest() {
    }

    public RegisterRequest(String login, String fullName, String password) {
        this.login    = login;
        this.fullName = fullName;
        this.password = password;
    }

    boolean isRegisterFormFillCorect (){
        boolean isCorect = false;
        return isCorect;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
