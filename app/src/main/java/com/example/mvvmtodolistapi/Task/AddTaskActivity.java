package com.example.mvvmtodolistapi.Task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mvvmtodolistapi.Model.ApiService;
import com.example.mvvmtodolistapi.Model.Days;
import com.example.mvvmtodolistapi.Model.Task;
import com.example.mvvmtodolistapi.R;
import com.google.android.material.textfield.TextInputEditText;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddTaskActivity extends AppCompatActivity {

    private TextInputEditText edtTitle;
    private TaskViewModel viewModel;
    private View saveBtn;
    private Days days;
    private Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        days=getIntent().getParcelableExtra("sTra");
        viewModel=new TaskViewModel(new ApiService());

        saveBtn=findViewById(R.id.saveTaskAddTaskActivity);
        edtTitle=findViewById(R.id.edtTaskAddTask);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.addTask(days.getDayId(),edtTitle.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<Task>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                disposable=d;
                            }

                            @Override
                            public void onSuccess(Task task) {
                                Intent intent=new Intent();
                                intent.putExtra("nTra",task);
                                setResult(5050,intent);
                                finish();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
            }
        });
    }
}