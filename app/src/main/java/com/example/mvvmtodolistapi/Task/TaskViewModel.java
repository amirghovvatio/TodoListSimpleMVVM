package com.example.mvvmtodolistapi.Task;

import android.app.backup.BackupHelper;

import com.example.mvvmtodolistapi.Model.ApiService;
import com.example.mvvmtodolistapi.Model.Task;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;

public class TaskViewModel {
    private ApiService apiService;
    private BehaviorSubject<Boolean> visibilities= BehaviorSubject.create();
    public TaskViewModel(ApiService apiService){
        this.apiService=apiService;
    }

    public Single<List<Task>> getAllTask(int dayId){
        visibilities.onNext(true);
        return apiService.getTasks(dayId);
    }

    public Single<Boolean> deleteTask(int id){
        return apiService.deleteTask(id);
    }
    public Single<Task> addTask(int dayId,String title){
        return apiService.addTask(dayId,title);
    }

    public BehaviorSubject<Boolean> getVisibilities() {
        return visibilities;
    }
}
