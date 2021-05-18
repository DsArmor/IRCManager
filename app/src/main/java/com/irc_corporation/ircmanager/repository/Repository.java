package com.irc_corporation.ircmanager.repository;

import androidx.lifecycle.MutableLiveData;

import com.irc_corporation.ircmanager.model.Group;

import java.util.Date;
import java.util.List;

public interface Repository {
    boolean userExist(String email, String password);
    void refresh(String email, String password);
    MutableLiveData<List<Group>> getGroups();
    void addTask(String email, String password, String groupName, String newGroupTaskName, String newGroupTaskDescription, Date newGroupTaskDueDate);
    void createGroup(String email, String password, String newGroupName);
    void addMember(String email, String password, String groupName, String newMemberEmail);
    void createUser(String name, String email, String password);
    void leave(String email, String password, String groupName, String adminEmail);
    void kickMember(String email, String password, String userEmail, String groupName);
    void taskDone(String email, String password, String groupName, String taskName, String adminEmail);
    void delete(String email, String password, String groupName);
}
