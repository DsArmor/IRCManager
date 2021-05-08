package com.irc_corporation.ircmanager.adapters;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.models.GroupTask;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<GroupTask> tasks = new ArrayList<>();

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        CardView cv;
        cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_for_tasks, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        GroupTask task = tasks.get(position);
        viewHolder.textViewTaskName.setText(task.getName());
        viewHolder.textViewDescription.setText(task.getDescription());
        //todo: получить реальное название группы
        viewHolder.textViewGroupName.setText("Бульбага");
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<GroupTask> tasks) {
        this.tasks = tasks;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewTaskName;
        private final TextView textViewDescription;
        private final TextView textViewGroupName;
        private TextView textViewData;//под вопросом, подумай

        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            textViewTaskName = itemView.findViewById(R.id.task_name_in_card_tasks);
            textViewDescription = itemView.findViewById(R.id.task_description_in_card_tasks);
            textViewGroupName = itemView.findViewById(R.id.group_name_in_card_tasks);
        }
    }
}
