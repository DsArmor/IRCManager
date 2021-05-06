package com.irc_corporation.ircmanager.repository.JSON;

public class Leave {
    public User user;
    public Group group;
    public class User {
        public String email;
        public String password;
    }
    public class Group {
        public String name;
        class Admin {
            public String fullname;
            public String email;
        }
    }
}
