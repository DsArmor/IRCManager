package com.irc_corporation.ircmanager.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.irc_corporation.ircmanager.Listener;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.adapters.TaskViewAdapter;
import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.models.GroupTask;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;

import org.w3c.dom.ls.LSOutput;

import java.util.List;

public class TaskFragment extends Fragment implements View.OnClickListener{

    private Listener listener;
    Repository repository;
    private List<GroupTask> groupTasks;
    private static final String LOG_TAG = "TaskFragment";

    //todo: посмотри жизненный цикл фрагмента

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        repository = IRCRepository.getInstance();
        SharedPreferences prefs = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        Log.d(LOG_TAG, prefs.getString("email", "Login Not saved"));
        Log.d(LOG_TAG, prefs.getString("password", "Password Not saved"));
        repository.refresh(prefs.getString("email", ""), prefs.getString("password", ""));
        View rootView = inflater.inflate(R.layout.fragment_task, container, false);
        FloatingActionButton button = rootView.findViewById(R.id.add_new_task);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_tasks);

        //получение данных с сервера
        if ((prefs.contains("email") && prefs.contains("password"))) {
            List<Group> groupList = repository.getGroups();
            System.out.println("Сейчас тестим: " + groupList.size());
            groupTasks = repository.getAllTasks();
            String[] names = new String[groupTasks.size()];
            String[] descriptions = new String[names.length];
            int i = 0;
            for (Group group : groupList) {
                List<GroupTask> receivedTasks = group.getTasks();
                System.out.println("Before we die " + receivedTasks.size());
                int previousSumTasks = i;
                for (; i < receivedTasks.size() + previousSumTasks; i++) {
                    names[i] = receivedTasks.get(i - previousSumTasks).getName();
                    descriptions[i] = receivedTasks.get(i - previousSumTasks).getDescription();
                }
            }

            TaskViewAdapter adapter = new TaskViewAdapter(names, descriptions);
            recyclerView.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            button.setOnClickListener(this);

        }
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