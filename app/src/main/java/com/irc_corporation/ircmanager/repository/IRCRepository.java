package com.irc_corporation.ircmanager.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.irc_corporation.ircmanager.model.Group;
import com.irc_corporation.ircmanager.model.GroupTask;
import com.irc_corporation.ircmanager.repository.JSON.AddMemberRequestBody;
import com.irc_corporation.ircmanager.repository.JSON.AddTaskRequestBody;
import com.irc_corporation.ircmanager.repository.JSON.CreateGroupRequestBody;
import com.irc_corporation.ircmanager.repository.JSON.DeleteGroupQuery;
import com.irc_corporation.ircmanager.repository.JSON.KickMemberRequestBody;
import com.irc_corporation.ircmanager.repository.JSON.LeaveGroupRequestBody;
import com.irc_corporation.ircmanager.repository.JSON.RegistrationRequestBody;
import com.irc_corporation.ircmanager.repository.JSON.GetAllGroupsRequestBody;
import com.irc_corporation.ircmanager.repository.JSON.TaskDoneRequestBody;

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
        groups = new MutableLiveData<List<Group>>();
        groups.setValue(new ArrayList<Group>());
    }

    public static Repository getInstance() {
        //одиночка
        if (instance == null) {
            Log.d(LOG_TAG, "Создание нового репозитория");
            return instance = new IRCRepository();
        }
        return instance;
    }


    //todo userExist работает неправльно
    /*@Override
    public boolean userExist(String email, String password) {
        Log.d(LOG_TAG, "refreshing for user " + email + " - " + password);
        GetAllGroupsRequestBody jsonBody = new GetAllGroupsRequestBody();
        jsonBody.email = email;
        jsonBody.password = password;
        Object sync = new Object();
        Thread threadRefresh = new Thread() {
            @Override
            public void run() {
                synchronized (sync) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitService service = retrofit.create(RetrofitService.class);
                Call<List<Group>> call = service.view(jsonBody);
                try {
                    Response<List<Group>> response = call.execute();
                    List<Group> newGroups = response.body();
                    if (newGroups != null)
                        Collections.sort(newGroups);
                    if (newGroups != null) {
                        groups.postValue(newGroups);
                    }
                    if (response.code() == 400){
                        Log.d(LOG_TAG, "User does not exist 400");
                        groups = null;
                    }
                    Log.d(LOG_TAG, "Группы получены");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(LOG_TAG, "Группы НЕ получены");
                }
            }
            }
        };
        threadRefresh.start();
        synchronized (sync) {
            Log.d(LOG_TAG, "userExist = true");
            if (groups == null){
                groups.setValue(new ArrayList<Group>());
                return false;
            }
            return true;
        }
    }*/

    @Override
    public void taskDone(String email, String password, String groupName, String taskName, String adminEmail) {
        TaskDoneRequestBody jsonBody = new TaskDoneRequestBody();
        jsonBody.user.email = email;
        jsonBody.user.password = password;
        jsonBody.task.group.name = groupName;
        jsonBody.task.name = taskName;
        jsonBody.task.group.admin.email = adminEmail;
        Thread thread = new Thread() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitService service = retrofit.create(RetrofitService.class);
                Call<String> call = service.done(jsonBody);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d(LOG_TAG, response.message());
                        refresh(email, password);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        refresh(email, password);
                    }
                });
            }
        };
        thread.start();
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
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    RetrofitService service = retrofit.create(RetrofitService.class);
                    Call<List<Group>> call = service.view(jsonBody);
                    try {
                        Response<List<Group>> response = call.execute();
                        List<Group> newGroups = response.body();
                        if (newGroups != null) {
                            int countDone=0;
                            for (Group group : newGroups) {
                                for (GroupTask task : group.getTasks()) {
                                    if (!task.isDone()){
                                        Log.d(LOG_TAG, "task not done "+task.getName());
                                        countDone++;
                                    }
                                }
                            }
                            System.out.println(countDone);
                            Collections.sort(newGroups);
                            if (groups != null) {
                                groups.postValue(newGroups);
                            }
                            else {
                                groups = new MutableLiveData<List<Group>>();
                            }
                            Log.d(LOG_TAG, "поле group обновлено");
                        }
                        Log.d(LOG_TAG, "Группы получены");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d(LOG_TAG, "Группы НЕ получены");
                    }
                }
            //}
        };
        threadRefresh.start();
    }

    @Override
    public MutableLiveData<List<Group>> getGroups() {
        Log.d(LOG_TAG, "getGroups() возвращает: " + String.valueOf(groups.getValue().size()));
        return this.groups;
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
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                RetrofitService service = retrofit.create(RetrofitService.class);
                Call<String> call = service.addTask(jsonBody);
                try {
                    Response<String> response = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                refresh(email, password);
            }
        };
        thread.start();
    }

    @Override
    public void leave(String email, String password, String groupName, String adminEmail) {
        LeaveGroupRequestBody jsonBody = new LeaveGroupRequestBody();
        jsonBody.group.name = groupName;
        jsonBody.user.email = email;
        jsonBody.user.password = password;
        jsonBody.group.admin.email = adminEmail;
        Thread thread = new Thread() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitService service = retrofit.create(RetrofitService.class);
                Call<String> call = service.leave(jsonBody);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d(LOG_TAG, response.message());
                        refresh(email, password);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        refresh(email, password);
                    }
                });
            }
        };
        thread.start();
    }

    @Override
    public void kickMember(String email, String password, String userEmail, String groupName) {
        KickMemberRequestBody jsonBody = new KickMemberRequestBody();
        jsonBody.group.name = groupName;
        jsonBody.admin.email = email;
        jsonBody.admin.password = password;
        jsonBody.newMember.email = userEmail;
        Thread thread = new Thread() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitService service = retrofit.create(RetrofitService.class);
                Call<String> call = service.kickMember(jsonBody);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d(LOG_TAG, response.message());
                        refresh(email, password);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        refresh(email, password);
                    }
                });
            }
        };
        thread.start();
    }

    @Override
    public void createGroup(String email, String password, String newGroupName) {
        CreateGroupRequestBody jsonBody = new CreateGroupRequestBody();
        jsonBody.group.name = newGroupName;
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
                        refresh(email, password);
                    }
                });
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
                        refresh(email, password);
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
                        refresh(email, password);
                    }
                });
                Log.d(LOG_TAG, "");
            }
        };
        thread.start();
    }

    @Override
    public void delete(String email, String password, String groupName) {
        DeleteGroupQuery jsonBody = new DeleteGroupQuery();
        jsonBody.admin.email = email;
        jsonBody.group.name = groupName;
        jsonBody.admin.password = password;
        Thread thread = new Thread() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitService service = retrofit.create(RetrofitService.class);
                Call<String> call = service.delete(jsonBody);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d(LOG_TAG, response.message());
                        refresh(email, password);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        refresh(email, password);
                    }
                });
                Log.d(LOG_TAG, "");
            }
        };
        thread.start();
    }
}
