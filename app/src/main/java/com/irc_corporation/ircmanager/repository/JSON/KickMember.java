package com.irc_corporation.ircmanager.repository.JSON;

public class KickMember {
    public NewMember newMember; //название newMember, так и должно быть
    //todo если сенять здесь название, нужно менять и на сервере
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
