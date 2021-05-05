package com.irc_corporation.ircmanager.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.irc_corporation.ircmanager.Group;
import com.irc_corporation.ircmanager.Listener;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.Task;

import java.util.ArrayList;

public class AddTaskFragment extends DialogFragment implements View.OnClickListener {

    private Listener listener;
    private EditText name;
    private EditText description;
    private Button addButton;
    private Button exitButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView =
                inflater.inflate(R.layout.fragment_add_task, container, false);
        addButton = rootView.findViewById(R.id.add_task_complete);
        exitButton = rootView.findViewById(R.id.exit);
        addButton.setOnClickListener(this);

        name = rootView.findViewById(R.id.task_name);
        description = rootView.findViewById(R.id.description);

        //переделать под репозиторий
        Group.setGroups();
        String[] temp_groups = new String[Group.groups.size()];
        for (int i=0; i<temp_groups.length; i++){
            temp_groups[i] = Group.groups.get(i).getTitle();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, temp_groups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner_groups);
        spinner.setAdapter(adapter);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
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
        String name_string = name.getText().toString();
        String description_string = description.getText().toString();
        Task.tasks.add(new Task(name_string, description_string));
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
