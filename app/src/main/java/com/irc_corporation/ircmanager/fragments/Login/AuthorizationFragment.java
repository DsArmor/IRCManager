package com.irc_corporation.ircmanager.fragments.Login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.irc_corporation.ircmanager.Listener;
import com.irc_corporation.ircmanager.R;

public class AuthorizationFragment extends Fragment implements View.OnClickListener{

    private Listener listener;

    private Button enter;
    private Button registration;
    private EditText email;
    private EditText password;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (Listener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_authorization, container, false);

        email = rootView.findViewById(R.id.login);
        password = rootView.findViewById(R.id.password);
        enter = rootView.findViewById(R.id.enter_button);
        enter.setOnClickListener(this);
        registration = rootView.findViewById(R.id.registration_button);
        registration.setOnClickListener(this);

        return rootView;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registration_button:
                this.listener.onMyClick(0);
                break;
            case R.id.enter_button:
                //todo: сделать проверку на корректность введенных данных
                SharedPreferences prefs = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("email", email.getText().toString());
                editor.apply();
                editor = prefs.edit();
                editor.putString("password", password.getText().toString());
                editor.apply();
                editor.apply();
                this.listener.onMyClick(1);
        }
    }
}