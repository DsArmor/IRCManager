package com.irc_corporation.ircmanager.repository.JSON;

public class AddEvent {
    public Event event;
    public Admin admin;
    public Group group;

    private class Event {
        public String name;
        public String description;
    }
    private class Admin {
        public String fullname;
        public String password;
        public String email;
    }
    private class Group {
        public String name;
    }
}
