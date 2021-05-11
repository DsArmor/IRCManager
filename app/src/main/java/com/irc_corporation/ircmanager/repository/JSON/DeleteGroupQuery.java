package com.irc_corporation.ircmanager.repository.JSON;

public class DeleteGroupQuery {
    public Admin admin;
    public Group group;

    public class Admin {
        public String email;
        public String password;
    }
    public class Group {
        public String name;
    }
}
