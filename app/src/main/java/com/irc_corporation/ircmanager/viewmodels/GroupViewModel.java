package com.irc_corporation.ircmanager.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;

import java.util.List;

public class GroupViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<List<Group>> groups;

    public GroupViewModel() {
        repository = IRCRepository.getInstance();
        groups = repository.getGroups();
    }

    public MutableLiveData<List<Group>> getGroups() {
        return groups;
    }

}