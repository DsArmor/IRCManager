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

import com.irc_corporation.ircmanager.Listener;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.Task;
import com.irc_corporation.ircmanager.adapters.TaskViewAdapter;
import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.models.GroupTask;
import com.irc_corporation.ircmanager.repository.Repository;
import com.irc_corporation.ircmanager.repository.SimpleRepository;

import java.util.List;

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

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_tasks);

        //получение данных с сервера
        Repository repository = SimpleRepository.getInstance();
        List<com.irc_corporation.ircmanager.models.Group> groupList = repository.getGroups();
        List<GroupTask> groupTasks = repository.getAllTasks();
        String[] names = new String[groupTasks.size()];
        String[] descriptions = new String[names.length];
        int i=0;
        for (Group group : groupList){
            List<GroupTask> groupTasks1 = repository.getTaskFromGroup(group);
            int previousSumTasks=i;
            for (; i<groupTasks1.size()+previousSumTasks; i++){
                names[i] = groupTasks1.get(i-previousSumTasks).getName();
                descriptions[i] = groupTasks1.get(i-previousSumTasks).getDescription();
            }
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