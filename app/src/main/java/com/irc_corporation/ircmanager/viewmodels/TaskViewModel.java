package com.irc_corporation.ircmanager.viewmodels;

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
        tasks = new ArrayList<>();
        for (Group group : groups.getValue()) {
            for (GroupTask task : group.getTasks()) {
                task.setGroup(group);
                this.tasks.add(task);
            }
        }
        return tasks;
    }
}
