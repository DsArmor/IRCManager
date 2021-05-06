package com.irc_corporation.ircmanager.repository;

import android.widget.SimpleAdapter;


import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.models.GroupEvent;
import com.irc_corporation.ircmanager.models.GroupTask;
import com.irc_corporation.ircmanager.models.User;

import java.util.ArrayList;
import java.util.List;

public class SimpleRepository implements Repository{
    private static SimpleRepository instance;
    private List<Group> groups;
    private List<User> users;
    private List<GroupTask> tasks;
    private List<GroupEvent> events;

    private SimpleRepository() {
        users = new ArrayList<>();
        tasks = new ArrayList<>();
        events = new ArrayList<>();
        users.add(new User("d", "d"));
        users.add(new User("a", "a"));
        tasks.add(new GroupTask("1", "1", "1", true));
        tasks.add(new GroupTask("2", "2", "2", false));
        events.add(new GroupEvent("1", "1", "1"));
        events.add(new GroupEvent("2", "2", "2"));
        groups = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            groups.add(new Group(Integer.toString(i), new User(Integer.toString(i), Integer.toString(i)), users, tasks, events));
        }
    }

    @Override
    public boolean refresh(String email, String password) {
        return true;
    }

    @Override
    public List<Group> getGroups() {
        return groups;
    }

    @Override
    public List<GroupTask> getAllTasks() {
        ArrayList<GroupTask> tasks = new ArrayList<>();
        for (Group group: groups) {
            for (GroupTask task : group.getTasks()) {
                tasks.add(task);
            }
        }
        return  tasks;
    }

    @Override
    public List<GroupTask> getTaskFromGroup(Group group) {
        ArrayList<GroupTask> tasks = new ArrayList<>();
        for (Group gr: groups) {
            if (group == gr) {
                for (GroupTask task : group.getTasks()) {
                    tasks.add(task);
                }
                return  tasks;
            }
        }
        return tasks;
    }

    @Override
    public List<GroupTask> getCurrentTaskFromGroup(Group group, String date) {
        ArrayList<GroupTask> tasks = new ArrayList<>();
        for (Group gr: groups) {
            if (group == gr) {
                for (GroupTask task : group.getTasks()) {
                    tasks.add(task);
                }
                return  tasks;
            }
        }
        return tasks;
    }

    @Override
    public boolean addTask(String email, String password, Group group, GroupTask task) {
        for (Group gr: groups) {
            if (group == gr) {
                group.getTasks().add(task);
                return  true;
            }
        }
        return false;
    }

    @Override
    public boolean addGroup(String email, String password, Group group) {
        groups.add(group);
        return true;
    }

    @Override
    public boolean addMember(String email, String password, Group group, User user) {
        for (Group gr: groups) {
            if (group == gr) {
                group.getMembers().add(user);
                return  true;
            }
        }
        return false;
    }

    @Override
    public boolean addUser(String name, String email, String password) {
        return false;
    }

    public static Repository getInstance() {
        if (instance == null) {
            return new SimpleRepository();
        }
        return instance;
    }
}
