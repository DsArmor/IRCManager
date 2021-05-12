package com.irc_corporation.ircmanager.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.ViewModel;

import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;

public class AddGroupViewModel extends ViewModel{

    private String groupName;
    private SharedPreferences sharedPreferences;

    public AddGroupViewModel() {
        super();
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void addGroup(){
        String string_title = groupName.toString();
        Repository repository = IRCRepository.getInstance();
        repository.createGroup(sharedPreferences.getString("email", ""), sharedPreferences.getString("password", ""), string_title);
        //возможно, можно убрать
        repository.refresh(sharedPreferences.getString("email", ""), sharedPreferences.getString("password", ""));
    }
}
