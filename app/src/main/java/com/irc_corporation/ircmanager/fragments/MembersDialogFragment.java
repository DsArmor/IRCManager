package com.irc_corporation.ircmanager.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.repository.Repository;
import com.irc_corporation.ircmanager.repository.SimpleRepository;

public class MembersDialogFragment extends DialogFragment {

    public MembersDialogFragment(){
        //передать сюда группу и по ней найти всех ее участников
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_members_dialog, container, false);

        //todo: получить всех участников группы с сервера и положить их в адаптер
        //todo: по возможности написать свой адаптер для групп
//        Repository repository = SimpleRepository.getInstance();
//        repository.
//        String[] members = new String[];
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, new String[]{"fe'wf'", "eowqieqw"});
        ListView listMembers = (ListView) rootView.findViewById(R.id.list_members);
        listMembers.setAdapter(listAdapter);

        return rootView;
    }
}