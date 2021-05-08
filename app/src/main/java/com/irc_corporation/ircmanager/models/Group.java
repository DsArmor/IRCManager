package com.irc_corporation.ircmanager.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Group implements Serializable, Comparable<Group> {

    private String name;
    private User admin;
    private List<User> members;
    private List<GroupTask> tasks;
    private List<GroupEvent> events;

    public Group(String name, User admin, List<User> members, List<GroupTask> tasks, List<GroupEvent> events) {
        if (members == null){
            members = new ArrayList<>();
        }
        if (tasks == null){
            tasks = new ArrayList<>();
        }
        if (events == null){
            events = new ArrayList<>();
        }
        if (admin == null){
            admin = new User("", "");
        }
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

    public List<GroupTask> getCurrentTaskFromGroup(String data) { //date format: dd-mm-yy"
        List<GroupTask> result = new ArrayList<GroupTask>();
        for (GroupTask task : getTasks()) {
            if (task.getDueDate().equals(data)) {
                result.add(task);
            }
        }
        return result;
    }

    @Override
    public int compareTo(Group o) {
        if(this.getName().compareTo(o.name) == 0) {
            return this.getAdmin().getEmail().compareTo(o.getAdmin().getEmail());
        }
        else {
            return this.getName().compareTo(o.name);
        }
    }
}
