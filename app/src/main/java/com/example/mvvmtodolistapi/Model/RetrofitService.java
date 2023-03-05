package com.example.mvvmtodolistapi.Model;

import android.app.job.JobScheduler;

import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public interface RetrofitService {
    @POST("tasks/getAll")
    Single<List<Task>> getAllTask(@Body JsonObject jsonObject);

    @POST("tasks/add")
    Single<Task> addTask(@Body JsonObject jsonObject);

    @HTTP(method = "DELETE",path = "tasks/delete",hasBody = true)
    Single<Boolean> deleteTask(@Body JsonObject jsonObject);

    @POST("day/getAll")
    Single<List<Days>> getAllDay();

    @POST("day/add" )
    Single<Days> addDays(@Body JsonObject jsonObject);

    @HTTP(method = "DELETE",path = "day/delete",hasBody = true)
    Single<Boolean> deleteDays(@Body JsonObject jsonObject);
}
