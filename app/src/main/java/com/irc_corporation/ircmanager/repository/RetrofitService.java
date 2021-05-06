package com.irc_corporation.ircmanager.repository;

import com.irc_corporation.ircmanager.models.Group;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import com.irc_corporation.ircmanager.repository.JSON.*;

public interface RetrofitService {
    @POST("/groups/view")
    Call<List<Group>> view(@Body View query);

    @POST("/groups/leave")
    Response leave(@Body Leave query);

    @POST("/groups/addEvent")
    Response addEvent(@Body AddEvent query);

    @POST("/groups/done")
    Response done(@Body Done query);

    @POST("/groups/addTask")
    Response addTask(@Body AddTask query);

    @HTTP(method = "DELETE", path = "/groups/kickMember", hasBody = true)
    Response kickMember(@Body KickMember query);

    @POST("/groups/addMember")
    Response addMember(@Body AddMember query);

    @POST("/groups/create")
    Response create(@Body Create query);

    @POST("/users/registration")
    Response registration(@Body Registration query);
}
