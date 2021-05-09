package com.irc_corporation.ircmanager.viewmodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.Spinner;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;

import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;

import java.util.Date;

public class AddTaskViewModel extends ViewModel implements View.OnClickListener {
    private String taskName;
    private String taskDescription;
    private String group;
    private String checkedGroup;
    private Date dueDate;
    private String LOG_TAG = "AddTaskViewModel";
    private SharedPreferences sharedPreferences;
    private FragmentManager fragmentManager;


    public String getCheckedGroup() {
        return checkedGroup;
    }

    public void setCheckedGroup(String checkedGroup) {
        this.checkedGroup = checkedGroup;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public androidx.fragment.app.FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(androidx.fragment.app.FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
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
        this.dueDate = dueDate;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exit:
                break;
            case R.id.add_task_complete:
                if (checkedGroup != null && taskName != null && taskDescription != null && dueDate != null) {
                    Repository repository = IRCRepository.getInstance();
                    repository.addTask(sharedPreferences.getString("email", ""), sharedPreferences.getString("password", ""), checkedGroup, taskName, taskDescription, dueDate);
                }
        }
        fragmentManager.popBackStack();
    }
}
