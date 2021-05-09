package com.irc_corporation.ircmanager.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.irc_corporation.ircmanager.fragments.MembersDialogFragment;
import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.models.GroupTask;
import com.irc_corporation.ircmanager.models.User;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class MembersDialogViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<List<Group>> groups;
    private List<User> members;

    public MembersDialogViewModel(){
        repository = IRCRepository.getInstance();
        groups = repository.getGroups();
    }

    public MutableLiveData<List<Group>> getGroups() {
        return groups;
    }

//    Group group
    public List<User> getMembers() {
        //временный код
        members = new ArrayList<>();
        members.add(new User("Pasha", "pashok_лох"));
        System.out.println("добавился студент");
        return members;
    }


}
