package com.irc_corporation.ircmanager.adapters;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.text.CaseMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.databinding.CardForTasksBinding;
import com.irc_corporation.ircmanager.models.GroupTask;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<GroupTask> tasks = new ArrayList<>();
    private static final String LOG_TAG = "TaskAdapter";

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        CardForTasksBinding binding = DataBindingUtil.inflate(inflater, R.layout.card_for_tasks, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int position) {
        if(getItemCount()!=0){
            GroupTask task = tasks.get(position);
            viewHolder.binding.taskNameInCardTasks.setText(task.getName());
            viewHolder.binding.taskDescriptionInCardTasks.setText(task.getDescription());
            //todo: получить реальное название группы
            viewHolder.binding.groupNameInCardTasks.setText("Бульбаал");
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<GroupTask> tasks) {
        Log.d(LOG_TAG, "setTasks");
        this.tasks = tasks;
        Log.d(LOG_TAG, "В адаптере: " + this.tasks.size() + " элементов");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewData;//под вопросом, подумай
        CardForTasksBinding binding;

        public ViewHolder(@NonNull CardForTasksBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
