package com.example.mvvmtodolistapi.Model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.security.Signature;
import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private RetrofitService retrofitService;
    private Retrofit retrofit;

    public ApiService(){
        retrofit=new Retrofit.Builder()
                .baseUrl("https://amir.vipmive.com/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService=retrofit.create(RetrofitService.class);
    }

    public Single<List<Task>> getTasks(int dayId){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("id",dayId);
        return retrofitService.getAllTask(jsonObject);
    }
    public Single<Task> addTask(int dayId,String title){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("dayId",dayId);
        jsonObject.addProperty("title",title);

        return retrofitService.addTask(jsonObject);
    }
    public Single<Boolean> deleteTask(int taskId){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("id",taskId);

        return retrofitService.deleteTask(jsonObject);
    }

    public Single<List<Days>> getAllDays(){
        return retrofitService.getAllDay();
    }
    public Single<Days> addDays(String dayName,String date){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("dayName",dayName);
        jsonObject.addProperty("date",date);
        return retrofitService.addDays(jsonObject);
    }
    public Single<Boolean> deleteDay(int dayId){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("id",dayId);
        return retrofitService.deleteDays(jsonObject);
    }
}
