package com.github.yaroslavguschak.util;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;



public class Alert {
    private String message;
    private boolean isShow;

    public Alert() {
        message = "";
        isShow = false;
    }

    public Alert(String message, boolean isShow) {
        this.message = message;
        this.isShow = isShow;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getIsShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
