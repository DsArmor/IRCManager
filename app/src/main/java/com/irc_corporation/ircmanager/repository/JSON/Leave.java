package com.irc_corporation.ircmanager.repository.JSON;

public class Leave {
    public User user = new User();
    public Group group = new Group();
    public class User {
        public String email;
        public String password;
    }
    public class Group {
        public String name;
        public Admin admin = new Admin();
        class Admin {
            public String fullname;
            public String email;
        }
    }
}
