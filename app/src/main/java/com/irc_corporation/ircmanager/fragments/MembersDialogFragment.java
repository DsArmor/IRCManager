package com.irc_corporation.ircmanager.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.adapters.MemberAdapter;
import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;
import com.irc_corporation.ircmanager.viewmodels.GroupViewModel;
import com.irc_corporation.ircmanager.viewmodels.MembersDialogViewModel;

import java.util.List;

public class MembersDialogFragment extends DialogFragment {

    private static final String LOG_TAG = "MemberDialogFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_members_dialog, container, false);

        //todo: докрутить удаление пользователей
        //todo: получить всех участников группы с сервера и положить их в адаптер

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_members);

        MemberAdapter adapter = new MemberAdapter();
        recyclerView.setAdapter(adapter);

        MembersDialogViewModel membersDialogViewModel = new ViewModelProvider(this).get(MembersDialogViewModel.class);
//        membersDialogViewModel.getGroups().observe(this, new Observer<List<Group>>() {
//            @Override
//            public void onChanged(List<Group> groups) {
//                Log.d(LOG_TAG, "OnChanged");
//                adapter.setMembers(membersDialogViewModel.getMembers(//тут должно быть получение группы));
//                adapter.notifyDataSetChanged();
//            }
//        });

//        repository.
//        String[] members = new String[];
//        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(getActivity(),
//                android.R.layout.simple_list_item_1, new String[]{"fe'wf'", "eowqieqw"});
//        ListView listMembers = (ListView) rootView.findViewById(R.id.list_members);
//        listMembers.setAdapter(listAdapter);

        return rootView;
    }
}