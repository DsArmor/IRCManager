package com.irc_corporation.ircmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener{

    //todo: дополни xml полем для группы
    //todo: сделай тут обработку группы

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Button add = findViewById(R.id.add_task);
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        TextView name = findViewById(R.id.task_name);
        TextView description = findViewById(R.id.description);
        String name_string = name.getText().toString();
        String description_string = description.getText().toString();
        Task.tasks.add(new Task(name_string, description_string));
        finish();
    }
}