package com.irc_corporation.ircmanager.view.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.irc_corporation.ircmanager.view.Listener;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.view.adapters.TaskAdapter;
import com.irc_corporation.ircmanager.model.Group;
import com.irc_corporation.ircmanager.model.GroupTask;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;
import com.irc_corporation.ircmanager.viewmodel.TaskViewModel;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class TaskFragment extends Fragment implements View.OnClickListener{

    SharedPreferences prefs;

    private Listener listener;
    private static final String LOG_TAG = "TaskFragment";
    private RecyclerView recyclerView;
    private  TaskViewModel taskViewModel;
    SwipeRefreshLayout swipeRefresh;

    @SuppressLint("ResourceAsColor")
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
            swipeRefresh = rootView.findViewById(R.id.swipe_refresh);
            int c1 = getResources().getColor(R.color.light_green);
            int c2 = getResources().getColor(R.color.white);
            int c3 = getResources().getColor(R.color.light_green);
            swipeRefresh.setColorSchemeColors(c1, c2, c3);
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
                        GroupTask groupTask =((TaskAdapter)recyclerView.getAdapter()).getTasks().get(viewHolder.getAdapterPosition());
                        repository.taskDone(prefs.getString("email", ""),
                                prefs.getString("password", ""),
                                groupTask.getGroup().getName(),
                                groupTask.getName(),
                                groupTask.getGroup().getAdmin().getEmail());
                    }
                }

                @Override
                public void onChildDraw(@NonNull Canvas c,
                                        @NonNull RecyclerView recyclerView,
                                        @NonNull RecyclerView.ViewHolder viewHolder,
                                        float dX,
                                        float dY,
                                        int actionState,
                                        boolean isCurrentlyActive) {
                    new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                            .addSwipeRightActionIcon(R.drawable.baseline_done_24)
                            .addSwipeRightBackgroundColor(ContextCompat.getColor(getActivity(), R.color.light_green))
                            .create()
                            .decorate();
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            };
            new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

            taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
            taskViewModel.setSharedPreferences(prefs);
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
            //todo: убрать
            repository.refresh(prefs.getString("email", ""), prefs.getString("password", ""));
            button.setOnClickListener(this);

            swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    taskViewModel.refresh();
                    swipeRefresh.setRefreshing(false);
                }
            });
        }
        return rootView;
    }

    //привязка
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (Listener) context;
        taskViewModel.refresh();
        //todo: убрать по возможности обращение к репозиторию
        /*prefs = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        Log.d(LOG_TAG, prefs.getString("email", "Login Not saved"));
        Log.d(LOG_TAG, prefs.getString("password", "Password Not saved"));
        Repository repository = IRCRepository.getInstance();
        repository.refresh(prefs.getString("email", ""),prefs.getString("password", ""));*/
    }

    @Override
    public void onClick(View v) {
        if (listener!= null){
            //вызов метода onMyClick происходит в MainActivity
            listener.onMyClick(1);
        }
    }

}