package com.irc_corporation.ircmanager.repository.JSON;

public class Create {
    public Admin admin;
    public NewGroup newGroup;

    public class Admin {
        public String password;
        public String email;
    }
    public class NewGroup {
        public String name;
    }
}
