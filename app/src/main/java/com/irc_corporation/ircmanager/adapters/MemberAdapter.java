package com.irc_corporation.ircmanager.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.irc_corporation.ircmanager.DismissListener;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.fragments.MembersDialogFragment;
import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.models.User;
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
        CardView cv;
        cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_for_members, parent, false);
        return new MemberAdapter.ViewHolder(cv);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        User member = members.get(position);
        viewHolder.textViewMember.setText(member.getFullname());
        viewHolder.textViewNumberOfMember.setText(Integer.toString(position+1));

        //здесь нужно получить имя пользователя
        SharedPreferences prefs = viewHolder.itemView.getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        Repository repository = IRCRepository.getInstance();

        if (!prefs.getString("email", "").equals(admin.getEmail()) && !prefs.getString("email", "").equals(members.get(position).getEmail())){
            viewHolder.imageButtonDeleteMember.setVisibility(View.INVISIBLE);
        } else{
            if (!prefs.getString("email", "").equals(members.get(position).getEmail())){
                viewHolder.imageButtonDeleteMember.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        repository.kickMember(prefs.getString("email", ""), prefs.getString("password", ""), members.get(position).getEmail(), groupName);
                        repository.refresh(prefs.getString("email", ""), prefs.getString("password", ""));
                        Log.d(LOG_TAG, "onClickDeleteMember");
                    }
                });
            }
            else {
                viewHolder.imageButtonDeleteMember.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        repository.leave(prefs.getString("email", ""), prefs.getString("password", ""), groupName,  admin.getEmail());
                        repository.refresh(prefs.getString("email", ""), prefs.getString("password", ""));
                        fragment.dismiss();
                    }
                });
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

        private final TextView textViewMember;
        private final TextView textViewNumberOfMember;
        private final ImageButton imageButtonDeleteMember;
        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            textViewMember = itemView.findViewById(R.id.name_of_member);
            textViewNumberOfMember = itemView.findViewById(R.id.number_of_member);
            imageButtonDeleteMember = itemView.findViewById(R.id.delete_member);
        }
    }
}
