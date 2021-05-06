package com.irc_corporation.ircmanager.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.irc_corporation.ircmanager.Group;
import com.irc_corporation.ircmanager.Listener;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.adapters.TaskViewAdapter;


public class GroupFragment extends Fragment implements View.OnClickListener{

    private Listener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Group.setGroups();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.fragment_group, container, false);
        FloatingActionButton button = rootView.findViewById(R.id.add_new_group);
        button.setOnClickListener(this);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_groups);
        String[] titles = new String[Group.groups.size()];
        for (int i=0; i<titles.length; i++){
            titles[i] = Group.groups.get(i).getTitle();
        }
        TaskViewAdapter adapter = new TaskViewAdapter(titles);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
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
        //todo: нужно порешать за сервер
    }

}