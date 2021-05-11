package com.irc_corporation.ircmanager.viewmodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.Spinner;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddTaskViewModel extends ViewModel {
    private String taskName;
    private String taskDescription;
    private String group;
    private String checkedGroup;
    private Date dueDate;
    private String LOG_TAG = "AddTaskViewModel";
    private SharedPreferences sharedPreferences;
    private FragmentManager fragmentManager;
    private Repository repository;
    private MutableLiveData<List<Group>> groups;

    public AddTaskViewModel(){
        repository = IRCRepository.getInstance();
        groups = repository.getGroups();
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public String[] getGroups(){
        List<Group> groupList = groups.getValue();

        List<Group> currentGroup = new ArrayList<>();
        for (int i=0; i<groupList.size(); i++){
            if (groupList.get(i).getAdmin().getEmail().equals(sharedPreferences.getString("email", ""))){
                currentGroup.add(groupList.get(i));
            }
        }

        String[] temp_groups = new String[currentGroup.size()];
        for (int i=0; i<currentGroup.size(); i++){
            temp_groups[i]=currentGroup.get(i).getName();
        }
        return temp_groups;
    }


    public String getCheckedGroup() {
        return checkedGroup;
    }

    public void setCheckedGroup(String checkedGroup) {
        this.checkedGroup = checkedGroup;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        Log.d(LOG_TAG, "setDueDate");
        this.dueDate = dueDate;
    }

    public void addTask(){
        repository.addTask(sharedPreferences.getString("email", ""),
                sharedPreferences.getString("password", ""),
                checkedGroup,
                taskName,
                taskDescription,
                dueDate);
    }
}
