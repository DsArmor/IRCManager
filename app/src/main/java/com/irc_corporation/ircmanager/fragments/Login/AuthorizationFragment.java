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
import com.irc_corporation.ircmanager.databinding.FragmentAuthorizationBinding;
import com.irc_corporation.ircmanager.viewmodels.Authorization;

public class AuthorizationFragment extends Fragment /*implements View.OnClickListener*/{

    private Listener listener;
    private FragmentAuthorizationBinding binding;

    /*private Button enter;
    private Button registration;
    private EditText email;
    private EditText password;*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (Listener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_authorization, container, false);
        Authorization auth = new Authorization(listener, getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE));
        binding.setAuthorization(auth);


        return binding.getRoot();
    }
}