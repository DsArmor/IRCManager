package com.irc_corporation.ircmanager.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.models.GroupTask;
import com.irc_corporation.ircmanager.repository.JSON.AddMemberRequestBody;
import com.irc_corporation.ircmanager.repository.JSON.AddTaskRequestBody;
import com.irc_corporation.ircmanager.repository.JSON.CreateGroupRequestBody;
import com.irc_corporation.ircmanager.repository.JSON.RegistrationRequestBody;
import com.irc_corporation.ircmanager.repository.JSON.GetAllGroupsRequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class IRCRepository implements Repository{

    private MutableLiveData<List<Group>> groups;
    private static Repository instance;
    private final static String URL = "https://to-do-server-for-samsung.herokuapp.com/";
    private final static String LOG_TAG = "repo";

    private IRCRepository() {
        groups = new MutableLiveData<>();
    }

    public static Repository getInstance() {
        //одиночка
        if (instance == null) {
            Log.d(LOG_TAG, "Создание нового репозитория");
            return instance = new IRCRepository();
        }
        return instance;
    }

    @Override
    public void refresh(String email, String password){
        Log.d(LOG_TAG, "refreshing for user " + email + " - " + password);
        GetAllGroupsRequestBody jsonBody = new GetAllGroupsRequestBody();
        jsonBody.email = email;
        jsonBody.password = password;
        Thread threadRefresh = new Thread() {
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
                        Response<List<Group>> response = call.execute();
                        groups = response.body() == null ? new ArrayList<>() : response.body();
                        Collections.sort(groups);
                        Log.d(LOG_TAG, "Группы получены");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d(LOG_TAG, "Группы НЕ получены");
                    }
                }
            }
        };
        threadRefresh.start();
    }

    @Override
    public MutableLiveData<List<Group>> getGroups() {
        synchronized (groups) {
            Log.d(LOG_TAG, "выводится размер группы: " + Integer.toString(groups.size()));
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
        Log.d(LOG_TAG, "количество тасков: " + Integer.toString(result.size()));
        return result;
    }

    @Override
    public void addTask(String email, String password, String groupName, String newGroupTaskName, String newGroupTaskDescription, Date newGroupTaskDueDate) {
        AddTaskRequestBody jsonBody = new AddTaskRequestBody();
        Log.d(LOG_TAG, "создание группы с параметрами");
        jsonBody.admin.email = email;
        jsonBody.admin.password = password;
        jsonBody.task.name = newGroupTaskName;
        jsonBody.task.description = newGroupTaskDescription;
        jsonBody.task.dueDate = newGroupTaskDueDate;
        jsonBody.group.name = groupName;
        Thread thread = new Thread() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitService service = retrofit.create(RetrofitService.class);
                Call<String> call = service.addTask(jsonBody);
                try {
                    Response<String> response = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @Override
    public void createGroup(String email, String password, String newGroupName) {
        CreateGroupRequestBody jsonBody = new CreateGroupRequestBody();
        jsonBody.newGroup.name = newGroupName;
        jsonBody.admin.email = email;
        jsonBody.admin.password = password;
        Thread thread = new Thread() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitService service = retrofit.create(RetrofitService.class);
                Call<String> call = service.create(jsonBody);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d(LOG_TAG, response.message());
                        refresh(email, password);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                    }
                });
                Log.d(LOG_TAG, "");
            }
        };
        thread.start();
    }

    @Override
    public void addMember(String email, String password, String groupName, String newMemberEmail) {
        AddMemberRequestBody jsonBody = new AddMemberRequestBody();
        jsonBody.admin.email = email;
        jsonBody.admin.password = password;
        jsonBody.group.name = groupName;
        jsonBody.newMember.email = newMemberEmail;
        Thread thread = new Thread() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitService service = retrofit.create(RetrofitService.class);
                Call<String> call = service.addMember(jsonBody);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d(LOG_TAG, response.message());
                        refresh(email, password);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                    }
                });
                Log.d(LOG_TAG, "");
            }
        };
        thread.start();
    }

    @Override
    public void createUser(String name, String email, String password) {
        RegistrationRequestBody jsonBody = new RegistrationRequestBody();
        jsonBody.email = email;
        jsonBody.fullname = name;
        jsonBody.password = password;
        Thread thread = new Thread() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitService service = retrofit.create(RetrofitService.class);
                Call<String> call = service.registration(jsonBody);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d(LOG_TAG, response.message());
                        refresh(email, password);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                    }
                });
                Log.d(LOG_TAG, "");
            }
        };
        thread.start();
    }

    /*


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
        refresh(email, password);
        return true;
    }

    @Override
    public boolean createGroup(String email, String password, Group group) {
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
    }*/

    /*private class ThreadRefresh extends Thread{
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
    }*/


   /* private class AsyncTaskAddTask extends AsyncTask<AddTaskRequestBody, Void, Void> {

        @Override
        protected Void doInBackground(AddTaskRequestBody... addTasks) {
            AddTaskRequestBody json = addTasks[0];
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitService service = retrofit.create(RetrofitService.class);
            Call<String> call = service.addTask(json);
            try {
                call.execute();
                Log.d(LOG_TAG, "Запрос на создание задания отправлен");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(LOG_TAG, "Запрос на создание задания НЕ отправлен");
            }
            return null;
        }
    }*/

/*    private class AsyncTaskAddUser extends AsyncTask<RegistrationRequestBody, Void, Void> {
        @Override
        protected Void doInBackground(RegistrationRequestBody... registrations) {
            RegistrationRequestBody json = registrations[0];
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitService service = retrofit.create(RetrofitService.class);
            Call<String> call = service.registration(json);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d(LOG_TAG, response.message());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                }
            });
            Log.d(LOG_TAG, "Запрос на создание группы отправлен");
            return null;
        }
    }*/

/*    private class AsyncTaskAddMember extends AsyncTask<AddMemberRequestBody, Void, Void> {
        @Override
        protected Void doInBackground(AddMemberRequestBody... addMembers) {
            AddMemberRequestBody json = addMembers[0];
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitService service = retrofit.create(RetrofitService.class);
            Call<String> call = service.addMember(json);
            try {
                call.execute();
                Log.d(LOG_TAG, "Запрос на добавление пользователя отправлен");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(LOG_TAG, "Запрос на добавление пользователя НЕ отправлен");
            }
            return null;
        }
    }*/

   /* private class AsyncTaskAddGroup extends  AsyncTask<CreateGroupRequestBody, Void, Void> {
        @Override
        protected Void doInBackground(CreateGroupRequestBody... creates) {
            CreateGroupRequestBody json = creates[0];
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitService service = retrofit.create(RetrofitService.class);
            Call<String> call = service.create(json);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d(LOG_TAG, response.message());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                }
            });
            Log.d(LOG_TAG, "Запрос на создание группы отправлен");
            return null;
        }
    }*/
}
