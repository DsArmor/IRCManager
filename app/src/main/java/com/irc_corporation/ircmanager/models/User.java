package com.irc_corporation.ircmanager.models;


public class User {
    private String fullname;
    private String email;


    public User(String fullname, String email) {
        this.fullname = fullname;
        this.email = email;
    }


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
