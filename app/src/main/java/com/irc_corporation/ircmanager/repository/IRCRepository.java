package com.irc_corporation.ircmanager.repository;

import android.os.AsyncTask;
import android.util.Log;

import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.models.GroupTask;
import com.irc_corporation.ircmanager.models.User;
import com.irc_corporation.ircmanager.repository.JSON.AddTask;
import com.irc_corporation.ircmanager.repository.JSON.Registration;
import com.irc_corporation.ircmanager.repository.JSON.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IRCRepository implements Repository{
    List<Group> groups;
    private static Repository instance;
    private final static String URL = "https://to-do-server-for-samsung.herokuapp.com/";
    private final static String LOG_TAG = "repo";

    private IRCRepository() {}

    public static Repository getInstance() {
        if (instance == null) return new IRCRepository();
        return instance;
    }

    @Override
    public boolean refresh(String email, String password) {
        View jsonBody = new View();
        jsonBody.email = email;
        jsonBody.password = password;
        AsyncTaskRefresh asyncTaskRefresh = new AsyncTaskRefresh();
        asyncTaskRefresh.execute(jsonBody);
        return true;
    }

    @Override
    public List<Group> getGroups() {
        return this.groups;
    }

    @Override
    public List<GroupTask> getAllTasks() {
        ArrayList<GroupTask> result= new ArrayList<>();
        for (Group group: groups) {
            for (GroupTask task : group.getTasks()) {
                result.add(task);
            }
        }
        return result;
    }

    @Override
    public List<GroupTask> getTaskFromGroup(Group group) {
        ArrayList<GroupTask> result= new ArrayList<>();
        for (GroupTask task : group.getTasks()) {
            result.add(task);
        }
        return result;
    }

    @Override
    public List<GroupTask> getCurrentTaskFromGroup(Group group, String date) {
        return null;
    }//todo

    @Override
    public boolean addTask(String email, String password, Group group, GroupTask task) {
        AddTask jsonBody = new AddTask();
        jsonBody.admin.email = email;
        jsonBody.admin.password = password;
        jsonBody.task.name = task.getName();
        jsonBody.task.description = task.getDescription();
        jsonBody.group.name = group.getName();
        AsyncTaskAddTask asyncTaskAddTask = new AsyncTaskAddTask();
        asyncTaskAddTask.execute(jsonBody);
        refresh(email, password); //todo нужно ли после добавления группы обновлять репозитрорий?
        return true;
    }

    @Override
    public boolean addGroup(String email, String password, Group group) {
        return false;
    }

    @Override
    public boolean addMember(String email, String password, Group group, User user) {
        return false;
    }

    @Override
    public boolean addUser(String name, String email, String password) {
        Registration reg = new Registration();
        reg.email = email;
        reg.fullname = name;
        reg.password = password;
        AsyncTaskAddUser asyncTaskAddUser = new AsyncTaskAddUser();
        asyncTaskAddUser.execute(reg);
        return true;
    }

    private class AsyncTaskRefresh extends AsyncTask<View, Void, Void> {
        List<Group> receivedGroups;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            groups = receivedGroups;
        }

        @Override
        protected Void doInBackground(View... views) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitService service = retrofit.create(RetrofitService.class);
            Call<List<Group>> call = service.view(views[0]);
            try {
                Response<List<Group>> userResponse = call.execute();
                receivedGroups = userResponse.body();
                Log.d(LOG_TAG, "Группы получены");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(LOG_TAG, "Группы НЕ получены");
            }
            return null;
        }
    }

    private class AsyncTaskAddTask extends AsyncTask<AddTask, Void, Void> {

        @Override
        protected Void doInBackground(AddTask... addTasks) {
            AddTask json = addTasks[0];
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitService service = retrofit.create(RetrofitService.class);
            Call<Response> call = service.addTask(json);
            try {
                call.execute();
                Log.d(LOG_TAG, "Запрос на создание задания отправлен");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(LOG_TAG, "Запрос на создание задания НЕ отправлен");
            }
            return null;
        }
    }

    private class AsyncTaskAddUser extends AsyncTask<Registration, Void, Void> {
        @Override
        protected Void doInBackground(Registration... registrations) {
            Registration json = registrations[0];
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitService service = retrofit.create(RetrofitService.class);
            Call<Response> call = service.registration(json);
            try {
                call.execute();
                Log.d(LOG_TAG, "Запрос на создание пользователя отправлен");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(LOG_TAG, "Запрос на создание пользователя НЕ отправлен");
            }
            return null;
        }
    }
}
