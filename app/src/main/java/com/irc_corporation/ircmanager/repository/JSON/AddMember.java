package com.irc_corporation.ircmanager.repository.JSON;

public class AddMember {
    public NewMember newMember;
    public Admin admin;
    public Group group;

    private class NewMember {
        public String email;
    }
    private  class Admin {
        public String fullname;
        public String password;
        public String email;
    }
    private class Group {
        public String name;
    }
}
