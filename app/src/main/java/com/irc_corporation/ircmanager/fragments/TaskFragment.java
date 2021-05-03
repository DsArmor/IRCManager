package com.irc_corporation.ircmanager.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.irc_corporation.ircmanager.AddTaskActivity;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.Task;
import com.irc_corporation.ircmanager.adapters.TaskViewAdapter;

public class TaskFragment extends Fragment implements View.OnClickListener{

    TaskViewFragment fragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.fragment_task, container, false);
        FloatingActionButton button = rootView.findViewById(R.id.add_new_task);
        button.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        //todo: нужно сделать пересыл этой информации в main activity
        //todo: где то нужно сделать перепривязку фрагмента, чтобы после добавления таска он отоброжался
        //todo: чекни жизненный цикл метод onResume()
        Intent intent = new Intent(getActivity(), AddTaskActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (fragment == null){
            fragment = new TaskViewFragment();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.tasks_container, fragment).commit();
        } else{
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.detach(fragment).attach(fragment).commit();
            System.out.println(Task.tasks.size());
        }
    }
}