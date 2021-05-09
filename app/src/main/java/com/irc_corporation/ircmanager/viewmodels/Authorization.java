package com.irc_corporation.ircmanager.viewmodels;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.irc_corporation.ircmanager.Listener;
import com.irc_corporation.ircmanager.LoginActivity;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;

import java.util.Observable;

public class Authorization implements View.OnClickListener {
    private String email;
    private String password;
    private Listener listener;
    private SharedPreferences sharedPreferences;
    private static final String LOG_TAG = "authorization";

    public Authorization(Listener listener, SharedPreferences sharedPreferences) {
        this.listener = listener;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void onClick(View v) {
        Repository repository = IRCRepository.getInstance();
        switch (v.getId()) {
            case R.id.registration_button:
                this.listener.onMyClick(0);
                break;
            case R.id.enter_button:
                if (repository.userExist(email, password)) {
                    Log.d(LOG_TAG, "userExist = true");
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", email);
                    editor.apply();
                    editor = sharedPreferences.edit();
                    editor.putString("password", password);
                    editor.apply();
                    editor.apply();
                    this.listener.onMyClick(1);
                }
                break;
        }
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
}
