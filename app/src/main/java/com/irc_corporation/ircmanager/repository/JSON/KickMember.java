package com.irc_corporation.ircmanager.repository.JSON;

public class KickMember {
    public NewMember newMember; //название newMember, так и должно быть
    //todo если сенять здесь название, нужно менять и на сервере
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
