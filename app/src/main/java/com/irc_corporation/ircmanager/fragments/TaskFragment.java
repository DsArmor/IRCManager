package com.irc_corporation.ircmanager.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.irc_corporation.ircmanager.Listener;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.Task;
import com.irc_corporation.ircmanager.adapters.TaskViewAdapter;

public class TaskFragment extends Fragment implements View.OnClickListener{

    private Listener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Task.setTasks();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.fragment_task, container, false);
        FloatingActionButton button = rootView.findViewById(R.id.add_new_task);

        RecyclerView recyclerView = rootView.findViewById(R.id.temp_recycler);
        String[] names = new String[Task.tasks.size()];
        String[] descriptions = new String[Task.tasks.size()];
        for (int i=0; i<names.length; i++){
            names[i] = Task.tasks.get(i).getName();
            descriptions[i] = Task.tasks.get(i).getDescription();
        }
        TaskViewAdapter adapter =new TaskViewAdapter(names, descriptions);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        button.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (Listener) context;
    }

    @Override
    public void onClick(View v) {
        if (listener!= null){
            listener.onMyClick(1);
        }
    }

}