package com.irc_corporation.ircmanager.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.databinding.CardForMembersBinding;
import com.irc_corporation.ircmanager.view.fragments.MembersDialogFragment;
import com.irc_corporation.ircmanager.model.Group;
import com.irc_corporation.ircmanager.model.User;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder>{

    private List<User> members = new ArrayList<>();
    private User admin;
    private String groupName;
    private static final String LOG_TAG = "MemberAdapter";
    private FrameLayout frameLayout;
    private MembersDialogFragment fragment;

    public MemberAdapter(Group group, MembersDialogFragment fragment) {
        Log.d(LOG_TAG, "MemberAdapter()");
        groupName = group.getName();
        this.fragment = fragment;
    }

    public void setMembers(List<User> members) {
        Log.d(LOG_TAG, "setMembers()");
        this.admin = members.get(members.size()-1);
        this.members = members;
    }

    public void setFrame(FrameLayout frame){
        this.frameLayout = frame;
    }

    public void setGroupName(String groupName) {
        Log.d(LOG_TAG, "setGroupName()");
        this.groupName = groupName;
    }

    @NonNull
    @Override
    public MemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(LOG_TAG, "onCreateViewHolder()");
        CardForMembersBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.card_for_members, parent, false);
        return new MemberAdapter.ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        User member = members.get(position);
        viewHolder.getBinding().setMember(member);
        viewHolder.getBinding().numberOfMember.setText(Integer.toString(position+1));
        SharedPreferences prefs = viewHolder.itemView.getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (!prefs.getString("email", "").equals(admin.getEmail()) && !prefs.getString("email", "").equals(members.get(position).getEmail())){
            viewHolder.getBinding().deleteMember.setVisibility(View.INVISIBLE);
        } else{
            if (!prefs.getString("email", "").equals(members.get(position).getEmail())){
                viewHolder.getBinding().setOnClick(fragment.getKickMembersCallback());
            }
            else {
                viewHolder.getBinding().setOnClick(fragment.getLeaveCallback());
            }
        }
        if (prefs.getString("email", "").equals(admin.getEmail())) {
            System.out.println("Мы находимся на проверке админа");
            frameLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        Log.d(LOG_TAG, "getItemCount(): " + String.valueOf(members.size()));
        return members.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private CardForMembersBinding binding;

        public ViewHolder(@NonNull CardForMembersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public CardForMembersBinding getBinding() {
            return binding;
        }

        public void setBinding(CardForMembersBinding binding) {
            this.binding = binding;
        }
    }
}
