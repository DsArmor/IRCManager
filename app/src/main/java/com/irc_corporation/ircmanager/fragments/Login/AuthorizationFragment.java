package com.irc_corporation.ircmanager.fragments.Login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.irc_corporation.ircmanager.Listener;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.databinding.FragmentAuthorizationBinding;
import com.irc_corporation.ircmanager.viewmodels.AuthorizationViewModel;

public class AuthorizationFragment extends Fragment{

    private Listener listener;
    private FragmentAuthorizationBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (Listener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_authorization, container, false);

        AuthorizationViewModel auth = new AuthorizationViewModel();
        auth.setSharedPreferences(getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE));

        binding.setAuthorizationViewModel(auth);
        binding.registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMyClick(0);
            }
        });
        binding.enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.logIn()) {
                    listener.onMyClick(1);
                }
                else {
                    Toast.makeText(getActivity(), "Неверный логин или пароль", Toast.LENGTH_LONG).show();
                }
            }
        });
        return binding.getRoot();
    }
}