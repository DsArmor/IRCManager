package com.irc_corporation.ircmanager.view.fragments.Login;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.irc_corporation.ircmanager.view.Listener;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.databinding.FragmentRegistrationBinding;
import com.irc_corporation.ircmanager.viewmodel.RegistrationViewModel;

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
        RegistrationViewModel reg = new RegistrationViewModel();
        reg.setSharedPreferences(getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE));
        binding.setRegistrationViewModel(reg);
        binding.registrationButtonInRegistr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reg.registration())
                    listener.onMyClick(1);
                else
                    Toast.makeText(getActivity(), "Регистрация не удалась", Toast.LENGTH_LONG).show();
            }
        });
        return binding.getRoot();
    }
}