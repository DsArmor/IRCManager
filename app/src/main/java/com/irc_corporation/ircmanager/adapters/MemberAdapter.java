package com.irc_corporation.ircmanager.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.models.User;

import java.util.ArrayList;
import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    private List<User> members = new ArrayList<>();

    public void setMembers(List<User> members) {
        this.members = members;
    }

    @NonNull
    @Override
    public MemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv;
        cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_for_members, parent, false);
        return new MemberAdapter.ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        User member = members.get(position);
        viewHolder.textViewMember.setText(member.getFullname());
        //тут должно быть получение номера
        viewHolder.textViewNumberOfMember.setText("13");
        viewHolder.imageButtonDeleteMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //удалить участника
                System.out.println("Должно типа удалиться");
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
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
