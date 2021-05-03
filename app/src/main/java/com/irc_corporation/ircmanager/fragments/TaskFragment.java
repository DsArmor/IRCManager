package com.irc_corporation.ircmanager.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.irc_corporation.ircmanager.AddTaskActivity;
import com.irc_corporation.ircmanager.R;

public class TaskFragment extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.fragment_task, container, false);
        FloatingActionButton button = rootView.findViewById(R.id.add_button);
        button.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), AddTaskActivity.class);
        startActivity(intent);
    }
}