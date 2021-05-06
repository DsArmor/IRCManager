package com.irc_corporation.ircmanager.repository.JSON;

public class AddMember {
    public NewMember newMember = new NewMember();
    public Admin admin = new Admin();
    public Group group = new Group();

    public class NewMember {
        public String email;
    }
    public  class Admin {
        public String fullname;
        public String password;
        public String email;
    }
    public class Group {
        public String name;
    }
}
