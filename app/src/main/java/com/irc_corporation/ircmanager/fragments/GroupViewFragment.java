package com.irc_corporation.ircmanager.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.irc_corporation.ircmanager.Group;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.adapters.TaskViewAdapter;

import java.util.ArrayList;

public class GroupViewFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //todo: убрать отсюда эту хрень для тестов
        super.onCreate(savedInstanceState);
        Group.setGroups();
//        ArrayList<String> temp = new ArrayList<>();
//        temp.add("fewfwe");
//        temp.add("frefer");
//        Group.groups.add(new Group("werfwe", temp));
//        Group.groups.add(new Group("popqw", temp));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //какой овер говнокод
        RecyclerView recyclerView =
                (RecyclerView) inflater.inflate(R.layout.recycler_groups, container, false);
        //todo: сделать так, чтобы данные приходили с локалиной бд или с сервера
        //как выгоднее и как правильнее пока хз
        String[] titles = new String[Group.groups.size()];
        for (int i=0; i<titles.length; i++){
            titles[i] = Group.groups.get(i).getTitle();
        }

        TaskViewAdapter adapter =new TaskViewAdapter(titles);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return recyclerView;
    }
}