package com.irc_corporation.ircmanager.viewmodel;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.irc_corporation.ircmanager.model.Group;
import com.irc_corporation.ircmanager.model.GroupTask;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class TaskViewModel extends ViewModel {

    private static final String LOG_TAG = "TaskViewModel";

    private Repository repository;
    private MutableLiveData<List<Group>> groups;
    private List<GroupTask> tasks = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

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
                if (!task.isDone()){
                    Log.d(LOG_TAG, "task not done "+task.getName());
                    System.out.println(task.isDone());
                    task.setGroup(group);
                    this.tasks.add(task);
                }
            }
        }
        return tasks;
    }

    public void refresh(){
        repository.refresh(sharedPreferences.getString("email", ""), sharedPreferences.getString("password", ""));
    }
}
