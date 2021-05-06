package com.irc_corporation.ircmanager.repository.JSON;

public class AddEvent {
    public Event event;
    public Admin admin;
    public Group group;

    public class Event {
        public String name;
        public String description;
    }
    public class Admin {
        public String fullname;
        public String password;
        public String email;
    }
    public class Group {
        public String name;
    }
}
