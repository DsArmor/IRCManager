package com.irc_corporation.ircmanager;

import java.util.ArrayList;

public class Group {
    //todo: изменить конфигурацию, см сервер
    private String title;
    private ArrayList<String> members;

    public static ArrayList<Group> groups;

    public static void setGroups() {
        if (groups == null){
            Group.groups = new ArrayList<>();
        }
    }

    public Group(String title, ArrayList<String> names){
        this.title = title;
        this.members = names;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getMembers() {
        return members;
    }
}
