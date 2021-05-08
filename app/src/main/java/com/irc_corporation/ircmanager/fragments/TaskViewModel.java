package com.irc_corporation.ircmanager.fragments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.models.GroupTask;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class TaskViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<List<Group>> groups;
    private List<GroupTask> tasks = new ArrayList<>();

    public TaskViewModel() {
        repository = IRCRepository.getInstance();
        groups = repository.getGroups();
    }

    //todo: отпарсить таски по дате и вернуть


    public MutableLiveData<List<Group>> getGroups() {
        return groups;
    }

    public List<GroupTask> getTasks() {
//        String[] names = new String[groupTasks.size()];
//        String[] descriptions = new String[names.length];
//        int i = 0;
//        for (Group group : groupList) {
//            List<GroupTask> receivedTasks = group.getTasks();
//            int previousSumTasks = i;
//            for (; i < receivedTasks.size() + previousSumTasks; i++) {
//                names[i] = receivedTasks.get(i - previousSumTasks).getName();
//                descriptions[i] = receivedTasks.get(i - previousSumTasks).getDescription();
//            }
//        }
        return tasks;
    }
}
