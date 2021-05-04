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

    private String[] names;
    private String[] descriptions;

    //todo: Добавить даты

    //не знаю, хорошо ли создать тут отдел для работы с группами или написать новый recycler;
    private String[] titles;

    public TaskViewAdapter(String[] titles){
        this.titles = titles;
    }

    public TaskViewAdapter(String[] names, String[] descriptions) {
        this.names = names;
        this.descriptions = descriptions;
    }

    @Override
    public int getItemCount() {
        if (names!=null){
            return names.length;
        } else
            return titles.length;
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
        CardView cv;
        if (names != null){
            cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_for_tasks, parent, false);
        } else{
            cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_for_groups, parent, false);
        }
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewAdapter.ViewHolder viewHolder, final int position) {
        CardView cv = viewHolder.cardView;
        if (names!=null){
            TextView name = cv.findViewById(R.id.task_name_in_card);
            name.setText(names[position]);
            TextView description = cv.findViewById(R.id.task_description_in_card);
            description.setText(descriptions[position]);
        } else{
            TextView title = cv.findViewById(R.id.group_name);
            title.setText(titles[position]);
        }
    }
}
