package com.irc_corporation.ircmanager.viewmodels;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.models.User;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class AddGroupViewModel extends ViewModel implements View.OnClickListener {
    private String groupName;
    private SharedPreferences sharedPreferences;

    public AddGroupViewModel() {
        super();
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void onClick(View v) {
        onClickAddGroup();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    private void onClickAddGroup(){
        String string_title = groupName.toString();
        Repository repository = IRCRepository.getInstance();
        repository.createGroup(sharedPreferences.getString("email", ""), sharedPreferences.getString("password", ""), string_title);
        repository.refresh(sharedPreferences.getString("email", ""), sharedPreferences.getString("password", ""));
    }
}
