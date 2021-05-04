package com.irc_corporation.ircmanager.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.irc_corporation.ircmanager.Listener;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.Task;

public class AddTaskFragment extends DialogFragment implements View.OnClickListener {

    private Listener listener;
    private TextView name;
    private TextView description;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView =
                inflater.inflate(R.layout.fragment_add_task, container, false);
        Button button = rootView.findViewById(R.id.add_task_complete);
        button.setOnClickListener(this);

        name = rootView.findViewById(R.id.task_name);
        description = rootView.findViewById(R.id.description);

        return rootView;
//        getDialog().setTitle("Title!");
//        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.fragment_add_task, null);
//        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (Listener) context;
    }

    @Override
    public void onClick(View v) {
        String name_string = name.getText().toString();
        String description_string = description.getText().toString();
        Task.tasks.add(new Task(name_string, description_string));
        getActivity().getSupportFragmentManager().popBackStack();
    }
}