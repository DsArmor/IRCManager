package com.irc_corporation.ircmanager.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.databinding.FragmentAddGroupBinding;
import com.irc_corporation.ircmanager.viewmodel.AddGroupViewModel;

public class AddGroupFragment extends DialogFragment {
    private FragmentAddGroupBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_group, container, false);
        AddGroupViewModel addGroupViewModel = new ViewModelProvider(this).get(AddGroupViewModel.class);
        addGroupViewModel.setSharedPreferences(getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE));

        binding.setAddGroupViewModel(addGroupViewModel);
        binding.addGroupComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGroupViewModel.addGroup();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return binding.getRoot();
    }
}