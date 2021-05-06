package com.irc_corporation.ircmanager.repository;

import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.models.GroupTask;
import com.irc_corporation.ircmanager.models.User;

import java.util.List;

public interface Repository {
    boolean refresh(String email, String password);
    List<Group> getGroups();
    List<GroupTask> getAllTasks();
    List<GroupTask> getTaskFromGroup(Group group);
    List<GroupTask> getCurrentTaskFromGroup(Group group, String date); //Не просроченные, date = "dd-mm-yy"
    boolean addTask(String email, String password, Group group, GroupTask task);
    boolean addGroup(String email, String password, Group group);
    boolean addMember(String email, String password, Group group, User user);
}
