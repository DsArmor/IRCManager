package com.irc_corporation.ircmanager.adapters;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.models.GroupTask;

import java.util.ArrayList;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    private List<Group> groups = new ArrayList<>();

    @NonNull
    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        CardView cv;
        cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_for_groups, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        Group group = groups.get(position);
        viewHolder.textViewGroupName.setText(group.getName());
        viewHolder.textViewCountMembers.setText("4");
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewGroupName;
        private final TextView textViewCountMembers;
        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            textViewGroupName = itemView.findViewById(R.id.group_name_in_card_tasks);
            textViewCountMembers = itemView.findViewById(R.id.count_of_members);
        }
    }
}
