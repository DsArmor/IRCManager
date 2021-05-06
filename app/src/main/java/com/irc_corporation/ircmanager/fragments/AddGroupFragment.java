package com.irc_corporation.ircmanager.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.irc_corporation.ircmanager.DismissListener;
import com.irc_corporation.ircmanager.R;
import com.irc_corporation.ircmanager.UserTemp;
import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.models.User;
import com.irc_corporation.ircmanager.repository.IRCRepository;
import com.irc_corporation.ircmanager.repository.Repository;
import com.irc_corporation.ircmanager.repository.SimpleRepository;

import java.util.ArrayList;

public class AddGroupFragment extends DialogFragment implements View.OnClickListener{

    private DismissListener dismissListener;

    ArrayList<User> members;
    Button addUser;
    Button addGroup;
    EditText titleOfGroup;
    EditText editUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_group, container, false);
        members = new ArrayList<>();
        addGroup = rootView.findViewById(R.id.add_group_complete);
        addUser = rootView.findViewById(R.id.add_user);
        titleOfGroup = rootView.findViewById(R.id.edit_group);
        editUser = rootView.findViewById(R.id.edit_user);
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
        //работа с репозиторием
        //todo: отладить для настоящего репозитория
        //логин и пароль брать из конфигурации
        Repository repository = IRCRepository.getInstance();
        Group group =
                new Group(string_title,
                        null,
                        null,
                        null,
                        null);
        repository.addGroup("Почта4", "Пароль", group);
        for (User member : members){
            repository.addMember("Почта4", "Пароль", group, member);
        }
        System.out.println(repository.getGroups().size());
        //todo: нужно положить созданную группу на сервер
        //todo: в этом месте реализован интерфейс, но можно наверное проще
        //хотя я весь инет облазил, реализация своего интрефейса может даже лучше...
        repository.refresh("Почта4","Пароль");

        dismissListener.onDismiss();
        dismiss();
    }

    private void onClickAddUser(){
        if (members == null){
            members = new ArrayList<>();
        }
        String email_user = editUser.getText().toString();
        members.add(new User("", email_user));
        editUser.setText("");
        //тосты почему-то не работают
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
        }
    }
}