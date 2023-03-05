package com.example.mvvmtodolistapi.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {
    private int taskID;
    private String title;
    private boolean done;


    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.taskID);
        dest.writeString(this.title);
        dest.writeByte(this.done ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        this.taskID = source.readInt();
        this.title = source.readString();
        this.done = source.readByte() != 0;
    }

    public Task() {
    }

    protected Task(Parcel in) {
        this.taskID = in.readInt();
        this.title = in.readString();
        this.done = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
