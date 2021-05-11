package com.irc_corporation.ircmanager.repository;

import com.irc_corporation.ircmanager.models.Group;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import com.irc_corporation.ircmanager.repository.JSON.*;

public interface RetrofitService {
    @POST("/groups/view")
    Call<List<Group>> view(@Body GetAllGroupsRequestBody query);

    @POST("/groups/leave")
    Call<String> leave(@Body LeaveGroupRequestBody query);

    @POST("/groups/addEvent")
    Call<String> addEvent(@Body AddEventRequestBody query);

    @POST("/groups/done")
    Call<String> done(@Body TaskDoneRequestBody query);

    @POST("/groups/addTask")
    Call<String> addTask(@Body AddTaskRequestBody query);

    @POST("/groups/kickMember")
    Call<String> kickMember(@Body KickMemberRequestBody query);

    @POST("/groups/addMember")
    Call<String> addMember(@Body AddMemberRequestBody query);

    @POST("/groups/create")
    Call<String> create(@Body CreateGroupRequestBody query);

    @POST("/users/registration")
    Call<String> registration(@Body RegistrationRequestBody query);

    @POST("/groups/delete")
    Call<String> delete(@Body DeleteGroupQuery query);
}
