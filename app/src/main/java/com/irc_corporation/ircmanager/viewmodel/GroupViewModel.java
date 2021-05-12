package com.irc_corporation.ircmanager.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.irc_corporation.ircmanager.model.Group;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class GroupViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<List<Group>> groups;
    private SharedPreferences sharedPreferences;
    private static final String LOG_TAG = "groupViewModel";

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public GroupViewModel() {
        repository = IRCRepository.getInstance();
        groups = repository.getGroups();
    }

    public List<Group> getGroupAdmin() {
        String adminEmail = sharedPreferences.getString("email", "");
        List<Group> resultGroups = new ArrayList<>();
        for (Group group : getGroups().getValue()) {
            if (group.getAdmin().getEmail().equals(adminEmail))
                resultGroups.add(group);
        }
        return resultGroups;
    }

    public List<Group> getGroupsMember() {
        String adminEmail = sharedPreferences.getString("email", "");
        List<Group> resultGroups = new ArrayList<>();
        for (Group group : getGroups().getValue()) {
            if (!group.getAdmin().getEmail().equals(adminEmail))
                resultGroups.add(group);
        }
        return resultGroups;
    }

//    public boolean checkAccess(){
//        if (sharedPreferences.getString())
//        return false;
//    }

    public void delete(Group group) {
        Repository repository = IRCRepository.getInstance();
        repository.delete(
                sharedPreferences.getString("email", ""),
                sharedPreferences.getString("password", ""),
                group.getName()
        );
    }

    public void refresh(){
        repository.refresh(sharedPreferences.getString("email", ""), sharedPreferences.getString("password", ""));
    }

    public MutableLiveData<List<Group>> getGroups() {
        return groups;
    }

}
