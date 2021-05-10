package com.irc_corporation.ircmanager.models;


import java.util.Date;

public class GroupTask implements Comparable<GroupTask>{
    private String name;
    private String description;
    private Date dueDate;
    private boolean isDone;
    private Group group;

    public GroupTask(String name, String description, Date dueDate, boolean isDone) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.isDone = isDone;
    }

    @Override
    public int compareTo(GroupTask o) {
        return 0;
        //todo
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
