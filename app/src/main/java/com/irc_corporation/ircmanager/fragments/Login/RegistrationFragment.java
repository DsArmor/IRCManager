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
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;

public class RegistrationFragment extends Fragment implements View.OnClickListener{

    private Listener listener;

    private Button registration;
    private EditText name;
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
        View rootView =  inflater.inflate(R.layout.fragment_registration, container, false);

        registration = rootView.findViewById(R.id.registration_button_in_registr);
        registration.setOnClickListener(this);
        name = rootView.findViewById(R.id.name_in_registr);
        email = rootView.findViewById(R.id.mail_in_registr);
        password = rootView.findViewById(R.id.password_in_registr);

        return rootView;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        //todo: сделать проверку
        Repository repository = IRCRepository.getInstance();
        repository.createUser(name.getText().toString(), email.getText().toString(), password.getText().toString());
        SharedPreferences prefs = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", email.getText().toString());
        editor.apply();
        editor = prefs.edit();
        editor.putString("password", password.getText().toString());
        editor.apply();
        this.listener.onMyClick(1);
    }
}