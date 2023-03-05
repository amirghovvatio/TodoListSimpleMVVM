package com.example.mvvmtodolistapi.Day;

import com.example.mvvmtodolistapi.Model.ApiService;
import com.example.mvvmtodolistapi.Model.Days;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;

public class DayViewModel {
    private ApiService apiService;
    private BehaviorSubject<Boolean> shimmerVisibility=BehaviorSubject.create();
    public DayViewModel(ApiService apiService){
        this.apiService=apiService;
    }
    public Single<List<Days>> getDays(){
        shimmerVisibility.onNext(true);
        return apiService.getAllDays();
//                .doFinally(() -> shimmerVisibility.onNext(false));
    }
    public Single<Boolean> deleteDay(int id){
        return apiService.deleteDay(id);
    }
    public Single<Days> addDays(String dayName,String date){
        return apiService.addDays(dayName,date)
                .doOnError(throwable -> shimmerVisibility.onNext(false));
    }

    public BehaviorSubject<Boolean> getShimmerVisibility() {
        return shimmerVisibility;
    }
}
