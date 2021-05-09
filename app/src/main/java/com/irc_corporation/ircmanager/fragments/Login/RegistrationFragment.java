package com.irc_corporation.ircmanager.fragments.Login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.irc_corporation.ircmanager.Listener;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.databinding.FragmentRegistrationBinding;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;
import com.irc_corporation.ircmanager.viewmodels.Authorization;
import com.irc_corporation.ircmanager.viewmodels.Registration;

public class RegistrationFragment extends Fragment{

    private Listener listener;
    private FragmentRegistrationBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (Listener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false);
        Registration reg = new Registration(listener, getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE));
        binding.setRegistration(reg);
        return binding.getRoot();
    }
}