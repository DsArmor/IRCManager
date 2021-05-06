package com.irc_corporation.ircmanager.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Group implements Serializable {

    private String name;
    private User admin;
    private List<User> members;
    private List<GroupTask> tasks;
    private List<GroupEvent> events;

    public Group(String name, User admin, List<User> members, List<GroupTask> tasks, List<GroupEvent> events) {
        this.name = name;
        this.admin = admin;
        this.members = members;
        this.tasks = tasks;
        this.events = events;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<GroupTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<GroupTask> tasks) {
        this.tasks = tasks;
    }

    public List<GroupEvent> getEvents() { return events; }

    public void setEvents(List<GroupEvent> events) { this.events = events; }
}
