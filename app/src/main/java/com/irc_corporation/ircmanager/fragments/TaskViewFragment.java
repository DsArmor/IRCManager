package com.irc_corporation.ircmanager.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.Task;
import com.irc_corporation.ircmanager.adapters.TaskViewAdapter;

public class TaskViewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //какой овер говнокод
        Task.setTasks();
        Task.tasks.add(new Task("freer", "fregregrege"));
        Task.tasks.add(new Task("weqorwer", "l;'jymkwe"));

        @SuppressLint("ResourceType") RecyclerView recyclerView =(RecyclerView) inflater.inflate(R.layout.recycler_tasks, container, false);
        //todo: сделать так, чтобы данные приходили с локалиной бд или с сервера
        //как выгоднее и как правильнее пока хз
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
        return recyclerView;
    }
}
