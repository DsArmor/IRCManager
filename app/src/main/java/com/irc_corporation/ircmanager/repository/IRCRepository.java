package com.irc_corporation.ircmanager.repository;

import android.os.AsyncTask;
import android.util.Log;

import com.irc_corporation.ircmanager.models.Group;
import com.irc_corporation.ircmanager.models.GroupTask;
import com.irc_corporation.ircmanager.models.User;
import com.irc_corporation.ircmanager.repository.JSON.View;

import java.io.IOException;
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
        AsyncTaskRefresh asyncTaskRefresh = new AsyncTaskRefresh();
        asyncTaskRefresh.execute(email, password);
        return true;
    }

    @Override
    public List<Group> getGroups() {
        return this.groups;
    }

    @Override
    public List<GroupTask> getAllTasks() {
        return null;
    }

    @Override
    public List<GroupTask> getTaskFromGroup(Group group) {
        return null;
    }

    @Override
    public List<GroupTask> getCurrentTaskFromGroup(Group group, String date) {
        return null;
    }

    @Override
    public boolean addTask(String email, String password, Group group, GroupTask task) {
        return false;
    }

    @Override
    public boolean addGroup(String email, String password, Group group) {
        return false;
    }

    @Override
    public boolean addMember(String email, String password, Group group, User user) {
        return false;
    }

    private class AsyncTaskRefresh extends AsyncTask<String, Void, Void> {
        List<Group> receivedGroups;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            groups = receivedGroups;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String email = strings[0];
            String password = strings[1];
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitService service = retrofit.create(RetrofitService.class);
            View json = new View();
            json.email = email;
            json.password = password;
            Call<List<Group>> call = service.view(json);
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
}
