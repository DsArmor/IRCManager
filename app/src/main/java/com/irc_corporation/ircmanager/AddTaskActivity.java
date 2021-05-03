package com.irc_corporation.ircmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Button add = findViewById(R.id.add_button);
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        TextView name = findViewById(R.id.task_name);
        TextView description = findViewById(R.id.description);
        Task.tasks.add(new Task((String)name.getText(), (String)description.getText()));
    }
}