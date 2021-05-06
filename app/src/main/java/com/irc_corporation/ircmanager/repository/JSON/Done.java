package com.irc_corporation.ircmanager.repository.JSON;

public class Done {
    public User user = new User();
    public Task task = new Task();

    public class User {
        public String email;
        public String password;
    }
    public class Task{
        public String name;
        public Group group;

        private class Group {
            public String name;
            public Admin admin;

            private class Admin {
                public String email;
            }
        }
    }
}
