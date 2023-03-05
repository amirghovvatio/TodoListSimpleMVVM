package com.example.mvvmtodolistapi.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Days implements Parcelable {
    private int dayId;
    private String dayName;
    private String date;

    public int getDayId() {
        return dayId;
    }

    public void setDayId(int dayId) {
        this.dayId = dayId;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.dayId);
        dest.writeString(this.dayName);
        dest.writeString(this.date);
    }

    public void readFromParcel(Parcel source) {
        this.dayId = source.readInt();
        this.dayName = source.readString();
        this.date = source.readString();
    }

    public Days() {
    }

    protected Days(Parcel in) {
        this.dayId = in.readInt();
        this.dayName = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<Days> CREATOR = new Parcelable.Creator<Days>() {
        @Override
        public Days createFromParcel(Parcel source) {
            return new Days(source);
        }

        @Override
        public Days[] newArray(int size) {
            return new Days[size];
        }
    };
}
