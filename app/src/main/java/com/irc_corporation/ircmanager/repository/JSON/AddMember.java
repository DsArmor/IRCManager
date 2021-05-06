package com.irc_corporation.ircmanager.repository.JSON;

public class AddMember {
    public NewMember newMember;
    public Admin admin;
    public Group group;

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
