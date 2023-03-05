package com.example.mvvmtodolistapi.Task;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mvvmtodolistapi.Model.ApiService;
import com.example.mvvmtodolistapi.Model.Days;
import com.example.mvvmtodolistapi.Model.Task;
import com.example.mvvmtodolistapi.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TaskActivity extends AppCompatActivity implements TaskAdapter.TaskEventListener {
    private Days day;
    private TaskViewModel viewModel;
    private RecyclerView rvTaskList;
    private TaskAdapter adapter;
    private CompositeDisposable disposable;
    private View addTask;
    private ShimmerFrameLayout shimmerFrameLayout;
    private LinearLayout mainView;
    private LinearLayout emptyState;
    private static final int REQ = 295;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        disposable=new CompositeDisposable();
        day=getIntent().getParcelableExtra("days");
        viewModel=new TaskViewModel(new ApiService());
        adapter=new TaskAdapter(this);

        emptyState=findViewById(R.id.emptyStateTaskActivity);
        mainView=findViewById(R.id.linearTask);
        shimmerFrameLayout=findViewById(R.id.shimmerTask);
        addTask=findViewById(R.id.addTaskBtn);
        rvTaskList=findViewById(R.id.rvTaskActivity);

        shimmerFrameLayout.startShimmer();
        rvTaskList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        rvTaskList.setAdapter(adapter);

        viewModel.getAllTask(day.getDayId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Task>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Task> tasks) {
                        adapter.getList(tasks);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

        disposable.add(viewModel.getVisibilities()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if (aBoolean){
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        mainView.setVisibility(View.VISIBLE);
                    }else {
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        emptyState.setVisibility(View.VISIBLE);
                    }


                }));


        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TaskActivity.this,AddTaskActivity.class);
                intent.putExtra("sTra",day);
                startActivityIfNeeded(intent,REQ);
            }
        });
    }

    @Override
    public void onClick(Task task) {
        viewModel.deleteTask(task.getTaskID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        if (aBoolean)
                            adapter.deleteItem(task);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == 5050 && data != null){
            Task task=data.getParcelableExtra("nTra");
            adapter.addTask(task);
        }
    }
}