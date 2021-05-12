package com.irc_corporation.ircmanager.view.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.model.User;
import com.irc_corporation.ircmanager.view.adapters.MemberAdapter;
import com.irc_corporation.ircmanager.databinding.FragmentMembersDialogBinding;
import com.irc_corporation.ircmanager.model.Group;
import com.irc_corporation.ircmanager.view.callback.OnClickMember;
import com.irc_corporation.ircmanager.viewmodel.MembersDialogViewModel;

import java.util.List;

public class MembersDialogFragment extends DialogFragment {

    private static final String LOG_TAG = "MemberDialogFragment";
    private Group group;
    private FragmentMembersDialogBinding binding;
    private MembersDialogViewModel membersDialogViewModel;

    public MembersDialogFragment(Group group) {
        this.group = group;
    }

    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_members_dialog, container, false);
        binding.recyclerMembers.setLayoutManager(new LinearLayoutManager(getActivity()));
        MemberAdapter adapter = new MemberAdapter(group, this);
        binding.recyclerMembers.setAdapter(adapter);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (group.getAdmin().getEmail().equals(sharedPreferences.getString("email", ""))) {
            binding.frameInDialog.setVisibility(View.VISIBLE);
        }
        membersDialogViewModel = new ViewModelProvider(this).get(MembersDialogViewModel.class);
        membersDialogViewModel.setSharedPreferences(sharedPreferences);

        binding.setMembersDialogViewModel(membersDialogViewModel);
        membersDialogViewModel.getGroups().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groups) {
                Log.d(LOG_TAG, "OnChanged ");
                System.out.println("Размер массива участников группы: " + membersDialogViewModel.getMembers(group).size());
                adapter.setMembers(membersDialogViewModel.getMembers(group));
                adapter.notifyDataSetChanged();
            }
        });

        ImageButton addMemberButton = binding.addMemberButton;
        addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                membersDialogViewModel.addMember();
            }
        });
        if (sharedPreferences.getString("email", "").equals(group.getAdmin().getEmail())) {
            setAddMemberBarVisibility(true);
        }
        return binding.getRoot();


    }

    private OnClickMember leaveCallback = new OnClickMember() {
        @Override
        public void onClickMember(User member) {
            membersDialogViewModel.leave(member);
        }
    };

    private OnClickMember kickMembersCallback = new OnClickMember() {
        @Override
        public void onClickMember(User member) {
            membersDialogViewModel.kickMember(member);
        }
    };

    public OnClickMember getLeaveCallback() {
        return leaveCallback;
    }

    public OnClickMember getKickMembersCallback() {
        return kickMembersCallback;
    }

    public void setAddMemberBarVisibility(boolean visible) {
        if (visible) {
            binding.frameInDialog.setVisibility(View.VISIBLE);
        }
        else {
            binding.frameInDialog.setVisibility(View.GONE);
        }
    }
}