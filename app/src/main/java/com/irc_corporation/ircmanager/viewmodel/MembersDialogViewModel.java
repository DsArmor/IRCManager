package com.irc_corporation.ircmanager.viewmodel;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.irc_corporation.ircmanager.model.Group;
import com.irc_corporation.ircmanager.model.User;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class MembersDialogViewModel extends ViewModel {

    private final MutableLiveData<List<Group>> groups;
    private List<User> members = new ArrayList<>();
    private String newMemberEmail;
    private SharedPreferences sharedPreferences;
    private Group group;
    private static final String LOG_TAG = "MembersDialogViewModel";

    public MembersDialogViewModel(){
        Repository repository = IRCRepository.getInstance();
        groups = repository.getGroups();
    }

    public MutableLiveData<List<Group>> getGroups() {
        return groups;
    }

    public List<User> getMembers(Group group) {
        members = new ArrayList<User>();
        for (Group groupTemp : groups.getValue()){
            if (groupTemp.getAdmin().getEmail().equals(group.getAdmin().getEmail()) && groupTemp.getName().equals(group.getName())){
                group=groupTemp;
                break;
            }
        }
        this.group = group;
        List<User> tempMembers = group.getMembers();

        User admin=null;
        for (int i = 0; i<tempMembers.size(); i++){
            if (!tempMembers.get(i).getFullname().equals(group.getAdmin().getFullname())){
                members.add(tempMembers.get(i));
            } else{
                admin = tempMembers.get(i);
            }
        }
        members.add(admin);
        return members;
    }

    public void addMember() {
        Repository repository = IRCRepository.getInstance();
        repository.addMember(sharedPreferences.getString("email", ""), sharedPreferences.getString("password", ""), group.getName(), newMemberEmail);
        repository.refresh(sharedPreferences.getString("email", ""), sharedPreferences.getString("password", ""));
        setNewMemberEmail("");
    }

    public void kickMember(User member) {
        Repository repository = IRCRepository.getInstance();
        repository.kickMember(sharedPreferences.getString("email", ""), sharedPreferences.getString("password", ""), member.getEmail(), group.getName());
        repository.refresh(sharedPreferences.getString("email", ""), sharedPreferences.getString("password", ""));
        Log.d(LOG_TAG, "onClickDeleteMember");
    }

    public void leave(User member) {
        Repository repository = IRCRepository.getInstance();
        repository.leave(sharedPreferences.getString("email", ""), sharedPreferences.getString("password", ""), group.getName(), member.getEmail());
        repository.refresh(sharedPreferences.getString("email", ""), sharedPreferences.getString("password", ""));
        Log.d(LOG_TAG, "onClickDeleteMember");
    }

    public String getNewMemberEmail() {
        return newMemberEmail;
    }

    public void setNewMemberEmail(String newMemberEmail) {
        this.newMemberEmail = newMemberEmail;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }
}
