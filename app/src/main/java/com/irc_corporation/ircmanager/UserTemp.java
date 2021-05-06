package com.irc_corporation.ircmanager;

public class UserTemp {
    private final String login;
    private final String password;

    private static UserTemp userInstance;

    private UserTemp(String login, String password){
        this.login = login;
        this.password = password;
    }

    public static UserTemp getInstance(String login, String password){
        if (userInstance ==null){
            return new UserTemp(login, password);
        }
        return userInstance;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

}
