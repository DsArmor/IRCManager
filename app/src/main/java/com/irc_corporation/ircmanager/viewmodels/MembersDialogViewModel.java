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

    private final MutableLiveData<List<Group>> groups;
    private final List<User> members = new ArrayList<>();

    public MembersDialogViewModel(){
        Repository repository = IRCRepository.getInstance();
        groups = repository.getGroups();
    }

    public MutableLiveData<List<Group>> getGroups() {
        return groups;
    }

    public List<User> getMembers(Group group) {
        List<User> tempMembers = group.getMembers();
        for (int i = 0; i<tempMembers.size(); i++){
            if (!tempMembers.get(i).getFullname().equals(group.getAdmin().getFullname())){
                members.add(tempMembers.get(i));
            }
        }
        return members;
    }
}
