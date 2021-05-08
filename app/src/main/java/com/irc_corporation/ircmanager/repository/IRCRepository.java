package com.irc_corporation.ircmanager.repository;

import android.os.AsyncTask;
import android.util.Log;

import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.models.GroupTask;
import com.irc_corporation.ircmanager.models.User;
import com.irc_corporation.ircmanager.repository.JSON.AddMemberRequestBody;
import com.irc_corporation.ircmanager.repository.JSON.AddTaskRequestBody;
import com.irc_corporation.ircmanager.repository.JSON.CreateGroupRequestBody;
import com.irc_corporation.ircmanager.repository.JSON.RegistrationRequestBody;
import com.irc_corporation.ircmanager.repository.JSON.GetAllGroupsRequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class IRCRepository implements Repository{
    private List<Group> groups;
    private static Repository instance;
    private final static String URL = "https://to-do-server-for-samsung.herokuapp.com/";
    private final static String LOG_TAG = "repo";

    private IRCRepository() {
        groups = new ArrayList<>();
    }

    public static Repository getInstance() {
        if (instance == null) {
            Log.d(LOG_TAG, "Создание нового репозитория");
            return instance = new IRCRepository();
        }
        return instance;
    }

    @Override
    public boolean refresh(String email, String password){
        Log.d(LOG_TAG, "refreshing for user " + email + " - " + password);
        GetAllGroupsRequestBody jsonBody = new GetAllGroupsRequestBody();
        jsonBody.email = email;
        jsonBody.password = password;
        ThreadRefresh threadRefresh = new ThreadRefresh(jsonBody);
        threadRefresh.start();
        return true;
    }

    @Override
    public List<Group> getGroups() {
        synchronized (groups) {
            Log.d(LOG_TAG, "выводится : " + Integer.toString(groups.size()));
            return this.groups;
        }
    }

    @Override
    public List<GroupTask> getAllTasks() {

        ArrayList<GroupTask> result= new ArrayList<>();
        for (Group group: getGroups()) {
            for (GroupTask task : group.getTasks()) {
                result.add(task);
            }
        }
        Log.d(LOG_TAG, "количество тасков : " + Integer.toString(result.size()));
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
        AddTaskRequestBody jsonBody = new AddTaskRequestBody();
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
         CreateGroupRequestBody jsonBody = new CreateGroupRequestBody();
         jsonBody.newGroup.name = group.getName();
         jsonBody.admin.email = email;
         jsonBody.admin.password = password;
         AsyncTaskAddGroup asyncTaskAddGroup = new AsyncTaskAddGroup();
         asyncTaskAddGroup.execute(jsonBody);
         return true;
    }

    @Override
    public boolean addMember(String email, String password, Group group, User user) {
        AddMemberRequestBody jsonBody = new AddMemberRequestBody();
        jsonBody.admin.email = email;
        jsonBody.admin.password = password;
        jsonBody.group.name = group.getName();
        jsonBody.newMember.email = user.getEmail();
        AsyncTaskAddMember asyncTaskAddMember = new AsyncTaskAddMember();
        asyncTaskAddMember.execute(jsonBody);
        return true;
    }

    @Override
    public boolean addUser(String name, String email, String password) {
        RegistrationRequestBody jsonBody = new RegistrationRequestBody();
        jsonBody.email = email;
        jsonBody.fullname = name;
        jsonBody.password = password;
        AsyncTaskAddUser asyncTaskAddUser = new AsyncTaskAddUser();
        asyncTaskAddUser.execute(jsonBody);
        return true;
    }

    private class ThreadRefresh extends Thread{
        private GetAllGroupsRequestBody jsonBody;

        ThreadRefresh(GetAllGroupsRequestBody jsonBody) {
            this.jsonBody = jsonBody;
        }

        @Override
        public void run() {
            synchronized (groups) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitService service = retrofit.create(RetrofitService.class);
                Call<List<Group>> call = service.view(jsonBody);
                try {
                    Response<List<Group>> userResponse = call.execute();
                    groups = userResponse.body() == null ? new ArrayList<>() : userResponse.body();
                    Log.d(LOG_TAG, "Группы получены");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(LOG_TAG, "Группы НЕ получены");
                }
            }
        }
    }


    private class AsyncTaskAddTask extends AsyncTask<AddTaskRequestBody, Void, Void> {

        @Override
        protected Void doInBackground(AddTaskRequestBody... addTasks) {
            AddTaskRequestBody json = addTasks[0];
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitService service = retrofit.create(RetrofitService.class);
            Call<Response<String>> call = service.addTask(json);
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

    //todo: мы меняли Response на Response<String>
    private class AsyncTaskAddUser extends AsyncTask<RegistrationRequestBody, Void, Void> {
        @Override
        protected Void doInBackground(RegistrationRequestBody... registrations) {
            RegistrationRequestBody json = registrations[0];
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitService service = retrofit.create(RetrofitService.class);
            Call<Response<String>> call = service.registration(json);
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

    private class AsyncTaskAddMember extends AsyncTask<AddMemberRequestBody, Void, Void> {
        @Override
        protected Void doInBackground(AddMemberRequestBody... addMembers) {
            AddMemberRequestBody json = addMembers[0];
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitService service = retrofit.create(RetrofitService.class);
            Call<Response<String>> call = service.addMember(json);
            try {
                call.execute();
                Log.d(LOG_TAG, "Запрос на добавление пользователя отправлен");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(LOG_TAG, "Запрос на добавление пользователя НЕ отправлен");
            }
            return null;
        }
    }

    private class AsyncTaskAddGroup extends  AsyncTask<CreateGroupRequestBody, Void, Void> {
        @Override
        protected Void doInBackground(CreateGroupRequestBody... creates) {
            CreateGroupRequestBody json = creates[0];
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitService service = retrofit.create(RetrofitService.class);
            Call<Response<String>> call = service.create(json);
            try {
                call.execute();
                Log.d(LOG_TAG, "Запрос на создание группы отправлен");
            } catch (IOException e) {
                //e.printStackTrace();
                //Log.d(LOG_TAG, "Запрос на создание группы НЕ отправлен");
            }
            return null;
        }
    }
}
