package com.irc_corporation.ircmanager.repository.JSON;

public class KickMemberRequestBody {
    public NewMember newMember = new NewMember(); //название newMember, так и должно быть
    //todo если менять здесь название, нужно менять и на сервере
    public Admin admin = new Admin();
    public Group group = new Group();

    public class NewMember {
        public String email;
    }
    public  class Admin {
        public String password;
        public String email;
    }
    public class Group {
        public String name;
    }
}
