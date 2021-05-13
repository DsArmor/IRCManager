package com.irc_corporation.ircmanager.view.adapters;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.databinding.CardForGroupsBinding;
import com.irc_corporation.ircmanager.databinding.CardForMembersBinding;
import com.irc_corporation.ircmanager.view.fragments.MembersDialogFragment;
import com.irc_corporation.ircmanager.model.Group;

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
        CardForGroupsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.card_for_groups, parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Group group = groups.get(position);
        viewHolder.getBinding().setGroup(group);
        viewHolder.getBinding().countOfMembers.setText(Integer.toString(group.getMembers().size()));
        //todo: как тут поставить setOnCLick не обращаясь к фрагменту, так еще и показать фргамент с текущей группой
        //не хочется создавать еще один интерфес для main, но к этому все и идет тогда, раз мы прокинем это в Groupfragment
        //тогда мы не можем там показать фрагмент, нарушит инкапсуляцию, поэтому мы должны будем прокинуть это в main activity?
        //это дорого обойдется....
//        viewHolder.getBinding().setOnClick();
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

        private CardForGroupsBinding binding;

        public ViewHolder(@NonNull CardForGroupsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public CardForGroupsBinding getBinding() {
            return binding;
        }

        public void setBinding(CardForGroupsBinding binding) {
            this.binding = binding;
        }
    }

}
