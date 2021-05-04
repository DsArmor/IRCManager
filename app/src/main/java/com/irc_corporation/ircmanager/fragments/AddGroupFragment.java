package com.irc_corporation.ircmanager.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.irc_corporation.ircmanager.DismissListener;
import com.irc_corporation.ircmanager.Group;
import com.irc_corporation.ircmanager.R;

import java.util.ArrayList;

public class AddGroupFragment extends DialogFragment implements View.OnClickListener{

    private DismissListener dismissListener;

    ArrayList<String> members;
    Button addUser;
    Button addGroup;
//    Button cancel;
    EditText titleOfGroup;
    EditText user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_group, container, false);

        addGroup = rootView.findViewById(R.id.add_group_complete);
        addUser = rootView.findViewById(R.id.add_user);
        titleOfGroup = rootView.findViewById(R.id.edit_group);
        user = rootView.findViewById(R.id.edit_user);
//        cancel = rootView.findViewById(R.id.cancel_creation_group);
        addUser.setOnClickListener(this);
        addGroup.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dismissListener = (DismissListener) context;
    }

    private void onClickAddGroup(){
        String string_title = titleOfGroup.getText().toString();
        Group.groups.add(new Group(string_title, members));
        //todo: в этом месте реализован интерфейс, но можно наверное проще
        //хотя я весь инет облазил, реализация своего интрефейса может даже лучше...
        dismissListener.onDismiss();
        dismiss();
    }

    private void onClickAddUser(){
        if (members == null){
            members = new ArrayList<>();
        }
        String string_user = user.getText().toString();
        members.add(string_user);
        user.setText("");
        //тосты почему-то не работают
//        Toast.makeText(getActivity().getApplicationContext(),
//                "Пользователь найден", Toast.LENGTH_SHORT);
    }

    //todo: почему-то dismiss тут не работает
//    private void onClickCancel(){
//        dismiss();
//    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_group_complete:
                onClickAddGroup();
                break;
            case R.id.add_user:
                onClickAddUser();
                break;
//            case R.id.cancel_creation_group:
//                onClickCancel();
        }
    }
}