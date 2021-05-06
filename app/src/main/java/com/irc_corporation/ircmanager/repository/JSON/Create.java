package com.irc_corporation.ircmanager.repository.JSON;

public class Create {
    public Admin admin;
    public NewGroup newGroup;

    private class Admin {
        public String password;
        public String email;
    }
    private class NewGroup {
        public String name;
    }
}
