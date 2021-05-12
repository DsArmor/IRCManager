package com.irc_corporation.ircmanager.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;

import com.irc_corporation.ircmanager.DismissListener;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.adapters.MemberAdapter;
import com.irc_corporation.ircmanager.databinding.FragmentMembersDialogBinding;
import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.models.User;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;
import com.irc_corporation.ircmanager.viewmodels.GroupViewModel;
import com.irc_corporation.ircmanager.viewmodels.MembersDialogViewModel;

import java.util.List;

public class MembersDialogFragment extends DialogFragment {

    private static final String LOG_TAG = "MemberDialogFragment";
    private Group group;
    private FragmentMembersDialogBinding binding;

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
        MembersDialogViewModel membersDialogViewModel = new ViewModelProvider(this).get(MembersDialogViewModel.class);
        membersDialogViewModel.setSharedPreferences(sharedPreferences);

        binding.setMembersDialogViewModel(membersDialogViewModel);
        membersDialogViewModel.getGroups().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groups) {
                Log.d(LOG_TAG, "OnChanged ");
                System.out.println("Размер массива участников группы: " + membersDialogViewModel.getMembers(group).size());
                adapter.setMembers(membersDialogViewModel.getMembers(group));
                adapter.setGroupName(group.getName());
                adapter.setFrame(binding.frameInDialog);
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

        return binding.getRoot();

    }
}