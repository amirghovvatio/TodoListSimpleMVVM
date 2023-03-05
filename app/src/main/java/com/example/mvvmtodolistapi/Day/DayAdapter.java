package com.example.mvvmtodolistapi.Day;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmtodolistapi.Model.Days;
import com.example.mvvmtodolistapi.R;

import java.util.ArrayList;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {
    private List<Days> daysList=new ArrayList<>();
    private DayEventListener dayEventListener;
    public DayAdapter(DayEventListener dayEventListener){
        this.dayEventListener=dayEventListener;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.day_list,parent,false);
        return new DayViewHolder(view);
    }
    public void addDays(Days days){
        this.daysList.add(0,days);
        notifyItemInserted(0);
    }
    public void getList(List<Days> days){
        this.daysList=days;
        notifyDataSetChanged();
    }
    public void deleteDays(Days days){
        int index=daysList.indexOf(days);
        daysList.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
            holder.bindDays(daysList.get(position));
    }

    @Override
    public int getItemCount() {
        return daysList.size();
    }

    public class DayViewHolder extends RecyclerView.ViewHolder {
        private CheckBox chkDay;
        private TextView tvDate;
        private ImageView imgDelete;
        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            chkDay=itemView.findViewById(R.id.chekcBoxDayList);
            tvDate=itemView.findViewById(R.id.tvDateDayList);
            imgDelete=itemView.findViewById(R.id.deleteDayList);
        }
        public void bindDays(Days days){
            chkDay.setText(days.getDayName());
            tvDate.setText(days.getDate());
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dayEventListener.onCick(days);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        dayEventListener.onLongClicked(days);
                }
            });
        }
    }
    public interface DayEventListener{
        void onCick(Days days);
        void onLongClicked(Days days);
    }
}
