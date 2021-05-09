package com.irc_corporation.ircmanager.viewmodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.irc_corporation.ircmanager.Listener;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;

public class Registration implements View.OnClickListener {
    private String name;
    private String email;
    private String password;
    private Listener listener;
    private SharedPreferences sharedPreferences;
    private static final String LOG_TAG = "registration";

    public Registration(Listener listener, SharedPreferences sharedPreferences) {
        this.listener = listener;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void onClick(View v) {
        //todo: сделать проверку
        Repository repository = IRCRepository.getInstance();
        repository.createUser(name, email, password);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email.toString());
        editor.apply();
        editor = sharedPreferences.edit();
        editor.putString("password", password);
        editor.apply();
        this.listener.onMyClick(1);
    }

    public String getEmail() {
        Log.d(LOG_TAG, "getEmail()");
        return email;
    }

    public void setEmail(String email) {
        Log.d(LOG_TAG, "setEmail()");
        this.email = email;
    }

    public String getPassword() {
        Log.d(LOG_TAG, "getPassword()");
        return password;
    }

    public void setPassword(String password) {
        Log.d(LOG_TAG, "setPassword()");
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}