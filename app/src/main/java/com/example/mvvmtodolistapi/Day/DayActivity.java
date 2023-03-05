package com.example.mvvmtodolistapi.Day;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mvvmtodolistapi.Model.ApiService;
import com.example.mvvmtodolistapi.Model.Days;
import com.example.mvvmtodolistapi.R;
import com.example.mvvmtodolistapi.Task.TaskActivity;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DayActivity extends AppCompatActivity implements DayAdapter.DayEventListener {
    private LinearLayout linearLayout;
    private RecyclerView rvDayList;
    private ShimmerFrameLayout shimmerFrameLayout;
    private DayViewModel dayViewModel;
    private DayAdapter dayAdapter;
    private CompositeDisposable compositeDisposable;
    private View addDayBtn;
    public static final int REQUEST_CODE = 789;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compositeDisposable=new CompositeDisposable();
        dayAdapter=new DayAdapter(this);
        dayViewModel=new DayViewModel(new ApiService());

        addDayBtn=findViewById(R.id.addDayBtn);
        rvDayList=findViewById(R.id.rvDayList);
        shimmerFrameLayout=findViewById(R.id.shimmerDay);
        linearLayout=findViewById(R.id.mainLinearLayout);

        rvDayList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        rvDayList.setAdapter(dayAdapter);
        shimmerFrameLayout.startShimmer();

        addDayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DayActivity.this,AddDayActivitiy.class);
                startActivityIfNeeded(intent,REQUEST_CODE);
            }
        });
        dayViewModel.getDays()
                .subscribeOn(Schedulers.io())
//                .delay(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Days>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Days> days) {
                        dayAdapter.getList(days);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        compositeDisposable.add(dayViewModel.getShimmerVisibility()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if (aBoolean){
                        shimmerFrameLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmer();
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }));


    }

    @Override
    public void onCick(Days days) {
        dayViewModel.deleteDay(days.getDayId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                            dayAdapter.deleteDays(days);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void onLongClicked(Days days) {
        Intent intent=new Intent(DayActivity.this, TaskActivity.class);
        intent.putExtra("days",days);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE &&
       resultCode == Activity.RESULT_OK &&
        data != null){

            Days days=data.getParcelableExtra("xtra");
            dayAdapter.addDays(days);
        }
    }
}