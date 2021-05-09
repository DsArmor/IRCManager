package com.irc_corporation.ircmanager.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.irc_corporation.ircmanager.Listener;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.adapters.GroupAdapter;
import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.viewmodels.GroupViewModel;

import java.util.List;


public class GroupFragment extends Fragment implements View.OnClickListener{

    private Listener listener;

    private static final String LOG_TAG = "GroupFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
//        SharedPreferences prefs = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
//        repository.refresh(prefs.getString("email", ""),prefs.getString("password", ""));
//        groupList = repository.getGroups().getValue();
        View rootView =
                inflater.inflate(R.layout.fragment_group, container, false);

        FloatingActionButton button = rootView.findViewById(R.id.add_new_group);
        button.setOnClickListener(this);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_groups);

        //получение данных с сервера

        GroupAdapter adapter = new GroupAdapter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        GroupViewModel groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        groupViewModel.getGroups().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groups) {
                Log.d(LOG_TAG, "OnChanged");
                adapter.setGroups(groups);
                adapter.notifyDataSetChanged();
            }
        });

        return rootView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (Listener) context;
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onMyClick(2);
        }
    }

}