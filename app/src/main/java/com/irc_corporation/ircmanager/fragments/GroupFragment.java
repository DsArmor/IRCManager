package com.irc_corporation.ircmanager.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.Task;


public class GroupFragment extends Fragment implements View.OnClickListener{

    GroupViewFragment fragment;

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
        return rootView;
    }

    @Override
    public void onClick(View v) {
        //todo: нужно порешать за сервер
//        Intent intent = new Intent(getActivity(), AddTaskActivity.class);
//        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (fragment == null){
            fragment = new GroupViewFragment();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.groups_container, fragment).commit();
        } else{
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.detach(fragment).attach(fragment).commit();
        }
    }
}