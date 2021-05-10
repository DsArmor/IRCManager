package com.irc_corporation.ircmanager.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

    SharedPreferences prefs;
    SwipeRefreshLayout swipeRefresh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        swipeRefresh = rootView.findViewById(R.id.swipe_refresh_group);

        int c1 = getResources().getColor(R.color.light_green);
        int c2 = getResources().getColor(R.color.white);
        int c3 = getResources().getColor(R.color.light_green);
        swipeRefresh.setColorSchemeColors(c1, c2, c3);

        //получение данных с сервера

        GroupAdapter adapter = new GroupAdapter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        GroupViewModel groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        prefs = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        groupViewModel.setSharedPreferences(prefs);
        groupViewModel.getGroups().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groups) {
                Log.d(LOG_TAG, "OnChanged");
                adapter.setGroups(groups);
                adapter.notifyDataSetChanged();
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                groupViewModel.refresh();
                swipeRefresh.setRefreshing(false);
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