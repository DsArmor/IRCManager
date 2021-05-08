package com.irc_corporation.ircmanager.repository;

import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.models.GroupTask;
import com.irc_corporation.ircmanager.models.User;

import java.util.List;

public interface Repository {
    void refresh(String email, String password);
    List<Group> getGroups();
    List<GroupTask> getAllTasks();
    void addTask(String email, String password, String groupName, String newGroupTaskName, String newGroupTaskDescription, String newGroupTaskDueDate);
    void createGroup(String email, String password, String newGroupName);
    void addMember(String email, String password, String groupName, String newMemberEmail);
    void createUser(String name, String email, String password);
}
