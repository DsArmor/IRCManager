package com.irc_corporation.ircmanager.adapters;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.fragments.MembersDialogFragment;
import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.models.GroupTask;
import com.irc_corporation.ircmanager.models.User;

import java.util.ArrayList;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    private List<Group> groups = new ArrayList<>();

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @NonNull
    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        CardView cv;
        cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_for_groups, parent, false);
        return new ViewHolder(cv);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Group group = groups.get(position);
        viewHolder.textViewGroupName.setText(group.getName());
        viewHolder.textViewCountMembers.setText(Integer.toString(group.getMembers().size()));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = ((AppCompatActivity)viewHolder.itemView.getContext());
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                DialogFragment dialog = new MembersDialogFragment(group);
                dialog.show(fragmentManager, ""); }
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewGroupName;
        private final TextView textViewCountMembers;

        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            textViewGroupName = itemView.findViewById(R.id.group_name_in_card_groups);
            textViewCountMembers = itemView.findViewById(R.id.count_of_members);
        }
    }
}
