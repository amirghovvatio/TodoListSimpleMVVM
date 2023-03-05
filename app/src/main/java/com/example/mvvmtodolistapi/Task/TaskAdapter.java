package com.example.mvvmtodolistapi.Task;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmtodolistapi.Model.Task;
import com.example.mvvmtodolistapi.R;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> taskList=new ArrayList<>();
    private TaskEventListener eventListener;
    public TaskAdapter(TaskEventListener taskEventListener){
        this.eventListener=taskEventListener;
    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list,parent,false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            holder.bindTask(taskList.get(position));
    }
    public void addTask(Task task){
        taskList.add(0,task);
        notifyItemInserted(0);
    }
    public void getList(List<Task> tasks){
        this.taskList=tasks;
        notifyDataSetChanged();
    }
    public void deleteItem(Task task){
        int index=taskList.indexOf(task);
        taskList.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private CheckBox chkTitle;
        private ImageView imgDelete;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            chkTitle=itemView.findViewById(R.id.checkBoxTaskList);
            imgDelete=itemView.findViewById(R.id.deleteTaskList);
        }
        public void bindTask(Task task){
            chkTitle.setText(task.getTitle());
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventListener.onClick(task);
                }
            });

        }
    }
    public interface TaskEventListener{
        void onClick(Task task);

    }
}
