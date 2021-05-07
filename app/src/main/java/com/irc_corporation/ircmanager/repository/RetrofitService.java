package com.irc_corporation.ircmanager.repository;

import com.irc_corporation.ircmanager.models.Group;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import com.irc_corporation.ircmanager.repository.JSON.*;

public interface RetrofitService {
    @POST("/groups/view")
    Call<List<Group>> view(@Body View query);

    @POST("/groups/leave")
    Call<Response> leave(@Body Leave query);

    //@Headers("Content-Type: text/html")
    @POST("/groups/addEvent")
    Call<Response> addEvent(@Body AddEvent query);

    @POST("/groups/done")
    Call<Response> done(@Body Done query);

    //@Headers("Content-Type: text/html")
    @POST("/groups/addTask")
    Call<Response<String>> addTask(@Body AddTask query);

    @HTTP(method = "DELETE", path = "/groups/kickMember", hasBody = true)
    Call<Response> kickMember(@Body KickMember query);

    //@Headers("Content-Type: text/html")
    @POST("/groups/addMember")
    Call<Response<String>> addMember(@Body AddMember query);

    @POST("/groups/create")
    Call<Response<String>> create(@Body Create query);

    @POST("/users/registration")
    Call<Response<String>> registration(@Body Registration query);
}
