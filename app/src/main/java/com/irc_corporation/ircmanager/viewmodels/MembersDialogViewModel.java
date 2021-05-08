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

    public MembersDialogViewModel(){
        repository = IRCRepository.getInstance();
        groups = repository.getGroups();
    }

    public String[] getMembers(Group group) {
        //временный код
        List<User> members = group.getMembers();
        String[] stringMembers = new String[members.size()];
        for (int i=0; i<members.size(); i++){
            stringMembers[i] = members.get(i).getFullname();
        }
        return stringMembers;
    }


}
