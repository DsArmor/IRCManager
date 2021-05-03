package com.irc_corporation.ircmanager;

import java.util.ArrayList;

public class Task {
    private String name;
    private String description;
    //todo: переделать на дату string
    private String start_date;
    private String end_date;

    public static ArrayList<Task> tasks;

    //вынужденная мера для отладки, лучше как то избавиться
    public static void setTasks() {
        if (tasks == null){
            tasks = new ArrayList<>();
        }
    }

    public Task(String name, String start_date, String end_date, String description){
        this.name = name;
        this.start_date =start_date;
        this.end_date = end_date;
        this.description = description;
    }

    public Task(String name, String start_date){
        this.name = name;
        this.start_date =start_date;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
