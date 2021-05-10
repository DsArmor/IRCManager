package com.irc_corporation.ircmanager.repository.JSON;

public class CreateGroupRequestBody {
    public Admin admin = new Admin();
    public Group group = new Group();

    public class Admin {
        public String password;
        public String email;
    }
    public class Group {
        public String name;
    }
}
