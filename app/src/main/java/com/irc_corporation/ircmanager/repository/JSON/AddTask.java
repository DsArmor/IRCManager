package com.irc_corporation.ircmanager.repository.JSON;

public class AddTask {
    public Task task;
    public Admin admin;
    public Group group;

    public class Task{
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
