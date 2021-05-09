package com.irc_corporation.ircmanager.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.adapters.MemberAdapter;
import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.models.User;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;
import com.irc_corporation.ircmanager.viewmodels.GroupViewModel;
import com.irc_corporation.ircmanager.viewmodels.MembersDialogViewModel;

import java.util.List;

public class MembersDialogFragment extends DialogFragment {

    private static final String LOG_TAG = "MemberDialogFragment";
    private Group group;
    private FrameLayout frameLayout;

    public MembersDialogFragment(Group group) {
        this.group = group;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_members_dialog, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_members);

        MemberAdapter adapter = new MemberAdapter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //todo: перевести на биндинг получение имени нового участника группы и нажатие на кнопку добавления
        
//        for (User member : members){
//            repository.addMember(prefs.getString("email", ""), prefs.getString("password", ""), string_title, member.getEmail());
//        }

        MembersDialogViewModel membersDialogViewModel = new ViewModelProvider(this).get(MembersDialogViewModel.class);
        membersDialogViewModel.getGroups().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groups) {
                Log.d(LOG_TAG, "OnChanged");
                adapter.setMembers(membersDialogViewModel.getMembers(group));
                //тут должно быть получение группы));
                adapter.notifyDataSetChanged();
            }
        });
        return rootView;

    }
}