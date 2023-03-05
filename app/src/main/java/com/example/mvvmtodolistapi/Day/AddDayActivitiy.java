package com.example.mvvmtodolistapi.Day;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mvvmtodolistapi.Model.ApiService;
import com.example.mvvmtodolistapi.Model.Days;
import com.example.mvvmtodolistapi.R;
import com.google.android.material.textfield.TextInputEditText;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddDayActivitiy extends AppCompatActivity {
    private DayViewModel viewModel;
    private TextInputEditText edtDayname;
    private TextInputEditText edtDate;
    private View saveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_day);

        viewModel=new DayViewModel(new ApiService());

        saveBtn=findViewById(R.id.saveDayAddDayActivity);
        edtDate=findViewById(R.id.edtDateAddDay);
        edtDayname=findViewById(R.id.edtDayAddDay);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.addDays(edtDayname.getText().toString(),edtDate.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<Days>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(Days days) {
                                Intent intent=new Intent();
                                intent.putExtra("xtra",days);
                                setResult(Activity.RESULT_OK,intent);
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