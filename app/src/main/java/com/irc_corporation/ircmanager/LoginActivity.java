package com.irc_corporation.ircmanager;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.irc_corporation.ircmanager.fragments.Login.AuthorizationFragment;
import com.irc_corporation.ircmanager.fragments.Login.RegistrationFragment;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;

public class LoginActivity extends AppCompatActivity implements Listener{

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Fragment fragment = new AuthorizationFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.registration_container, fragment).commit();

    }

    @Override
    public void onMyClick(int id) {
        switch (id) {
            case 0:
                Fragment fragment = new RegistrationFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.registration_container, fragment)
                        .commit();
                break;
            case 1:
                finish();
        }
    }
}