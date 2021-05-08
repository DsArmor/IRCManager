package com.irc_corporation.ircmanager.repository.JSON;

public class AddTaskRequestBody {
    public Task task = new Task();
    public Admin admin = new Admin();
    public Group group = new Group();

    public class Task{
        public String name;
        public String description;
        public String dueDate;
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
