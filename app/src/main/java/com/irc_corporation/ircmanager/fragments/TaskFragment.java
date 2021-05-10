package com.irc_corporation.ircmanager.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.irc_corporation.ircmanager.Listener;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.adapters.TaskAdapter;
import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;
import com.irc_corporation.ircmanager.viewmodels.TaskViewModel;

import java.util.List;

public class TaskFragment extends Fragment implements View.OnClickListener{

    SharedPreferences prefs;

    private Listener listener;
    private static final String LOG_TAG = "TaskFragment";
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "enter OnCreateView");
        View rootView = inflater.inflate(R.layout.fragment_task, container, false);

        //получение данных из viewModel
        if ((prefs.contains("email") && prefs.contains("password"))) {
            //todo: убрать обращение к репозиторию по возможности
            Repository repository = IRCRepository.getInstance();

            FloatingActionButton button = rootView.findViewById(R.id.add_new_task);

            //найдем ресайклер
            recyclerView = rootView.findViewById(R.id.recycler_tasks);
            TaskAdapter adapter = new TaskAdapter();
            recyclerView.setAdapter(adapter);

            ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    if (direction == ItemTouchHelper.RIGHT){
                        //произвести удаление таска
                        //для отладки
                        repository.taskDone(prefs.getString("email", ""),
                                prefs.getString("password", ""),
                                viewHolder.itemView.findViewById(R.id.group_name_in_card_tasks));
                    }
                }
            };
            new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

            TaskViewModel taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
            taskViewModel.getGroups().observe(this, new Observer<List<Group>>() {
                @Override
                public void onChanged(List<Group> groups) {
                    Log.d(LOG_TAG, "OnChanged");
                    adapter.setTasks(taskViewModel.getTasks());
                    adapter.notifyDataSetChanged();
                }
            });

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            repository.refresh(prefs.getString("email", ""), prefs.getString("password", ""));
            button.setOnClickListener(this);
        }
        return rootView;
    }

    //привязка
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (Listener) context;

        //todo: убрать по возможности обращение к репозиторию
        prefs = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        Log.d(LOG_TAG, prefs.getString("email", "Login Not saved"));
        Log.d(LOG_TAG, prefs.getString("password", "Password Not saved"));
        Repository repository = IRCRepository.getInstance();
        repository.refresh(prefs.getString("email", ""),prefs.getString("password", ""));
    }

    @Override
    public void onClick(View v) {
        if (listener!= null){
            //вызов метода onMyClick происходит в MainActivity
            listener.onMyClick(1);
        }
    }

}