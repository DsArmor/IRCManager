package com.irc_corporation.ircmanager.fragments.Login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.irc_corporation.ircmanager.Listener;
import com.irc_corporation.ircmanager.R;

public class RegistrationFragment extends Fragment implements View.OnClickListener{

    private Listener listener;

    private Button registration;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (Listener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_registration, container, false);

        registration = rootView.findViewById(R.id.registration_button_in_registr);
        registration.setOnClickListener(this);

        return rootView;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        //todo: сделать проверку на наличие пользователя
        //сохранить полученные данные в нужную конфигурацию
        this.listener.onMyClick(1);
    }
}