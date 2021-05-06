package com.irc_corporation.ircmanager.repository.JSON;

public class Create {
    public Admin admin = new Admin();
    public NewGroup newGroup = new NewGroup();

    public class Admin {
        public String password;
        public String email;
    }
    public class NewGroup {
        public String name;
    }
}
