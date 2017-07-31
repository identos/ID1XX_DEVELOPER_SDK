package com.identos.android.id100.demo.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

abstract class DataItem implements Parcelable{
    private final String title;

    DataItem(String title) {
        this.title = title;
    }

    abstract boolean isSection();

    String getTitle() {
        return title;
    }

    DataItem(Parcel in) {
        this.title = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
    }
}