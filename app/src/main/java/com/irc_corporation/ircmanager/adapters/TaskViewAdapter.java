package com.irc_corporation.ircmanager.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.irc_corporation.ircmanager.R;

public class TaskViewAdapter extends RecyclerView.Adapter<TaskViewAdapter.ViewHolder> {

    private final String[] names;
    private final String[] descriptions;

    //todo: Добавить даты

    public TaskViewAdapter(String[] names, String[] descriptions) {
        this.names = names;
        this.descriptions = descriptions;
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final CardView cardView;

        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }

    @NonNull
    @Override
    public TaskViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_for_tasks, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewAdapter.ViewHolder viewHolder, final int position) {
        CardView cv = viewHolder.cardView;
        TextView name = cv.findViewById(R.id.task_name_in_card);
        name.setText(names[position]);
        TextView description = cv.findViewById(R.id.task_description_in_card);
        description.setText(descriptions[position]);
    }
}
