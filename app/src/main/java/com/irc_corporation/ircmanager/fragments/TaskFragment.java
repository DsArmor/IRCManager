package com.irc_corporation.ircmanager.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

import java.util.List;

public class TaskFragment extends Fragment implements View.OnClickListener{

    private Listener listener;
    Repository repository;
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
            List<Group> groupList = repository.getGroups().getValue();
            System.out.println("Сейчас тестим: " + groupList.size());
            int countTasks = 0;
            for (Group group : groupList) {
                for (GroupTask task : group.getTasks()) {
                    countTasks++;
                }
            }
            String[] names = new String[countTasks];
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