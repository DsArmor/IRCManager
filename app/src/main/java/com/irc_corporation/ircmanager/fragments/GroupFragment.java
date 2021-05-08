package com.irc_corporation.ircmanager.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.irc_corporation.ircmanager.Listener;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.adapters.TaskViewAdapter;
import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;

import java.util.List;


public class GroupFragment extends Fragment implements View.OnClickListener{

    private Listener listener;

    private List<Group> groupList;
    private Repository repository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = IRCRepository.getInstance();


    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences prefs = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        repository.refresh(prefs.getString("email", ""),prefs.getString("password", ""));
        groupList = repository.getGroups().getValue();

        System.out.println("Размер массива групп внутри Групп Фрагмента"+groupList.size());
        View rootView =
                inflater.inflate(R.layout.fragment_group, container, false);
        FloatingActionButton button = rootView.findViewById(R.id.add_new_group);
        button.setOnClickListener(this);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_groups);

        //получение данных с сервера

        System.out.println(groupList.size());
        String[] titles = new String[groupList.size()];
        for (int i=0; i<titles.length; i++){
            titles[i] = groupList.get(i).getName();
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