package com.irc_corporation.ircmanager.models;


import java.util.Date;

public class GroupTask implements Comparable<GroupTask>{
    private String name;
    private String description;
    private Date dueDate;
    private boolean done;
    private Group group;

    public GroupTask(String name, String description, Date dueDate, boolean isDone) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.done = isDone;
    }

    @Override
    public int compareTo(GroupTask o) {
        if (dueDate != null && o.getDueDate() != null) {
            if (dueDate.compareTo(o.getDueDate()) == 0) {
                if (name.compareTo(o.getName()) == 0) {
                    return group.compareTo(o.getGroup());
                }
                return name.compareTo(o.getName());
            }
            return dueDate.compareTo(o.getDueDate());
        }
        else if (o.getDueDate() == null) {
            if (dueDate != null)
                return -1;
            else
                return name.compareTo(o.getName());
        }
        else if (dueDate == null) {
            if (o.getDueDate() != null)
                return 1;
            else
                return name.compareTo(o.getName());
        }
        else if (name.compareTo(o.getName()) == 0) {
            return group.compareTo(o.getGroup());
        }
        return name.compareTo(o.getName());
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
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
