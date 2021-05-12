package com.irc_corporation.ircmanager.viewmodels;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.irc_corporation.ircmanager.Listener;
import com.irc_corporation.ircmanager.LoginActivity;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;

import java.util.Observable;

public class AuthorizationViewModel  {
    private String email;
    private String password;
    private SharedPreferences sharedPreferences;
    private static final String LOG_TAG = "authorization";

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    //todo
    public boolean logIn() {
        Repository repository = IRCRepository.getInstance();
        //if (repository.userExist(email, password)) {
            Log.d(LOG_TAG, "userExist = true");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", email);
            editor.apply();
            editor = sharedPreferences.edit();
            editor.putString("password", password);
            editor.apply();
            editor.apply();
        //}
        //return (repository.userExist(sharedPreferences.getString("email", ""), sharedPreferences.getString("password", "")));
        return true;
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
