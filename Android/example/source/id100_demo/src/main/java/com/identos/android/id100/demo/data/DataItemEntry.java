package com.identos.android.id100.demo.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

class DataItemEntry extends DataItem implements Parcelable{
    private final String mValue;

    DataItemEntry(String title, String value) {
        super(title);

        mValue = value;
    }

    @Override
    boolean isSection() {
        return false;
    }

    String getValue() {
        return mValue;
    }

    private DataItemEntry(Parcel in) {
        super(in);

        mValue = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);

        dest.writeString(mValue);
    }

    public static final Creator<DataItemEntry> CREATOR = new Parcelable.Creator<DataItemEntry>() {
        @Override
        public DataItemEntry createFromParcel(Parcel in) {
            return new DataItemEntry(in);
        }

        @Override
        public DataItemEntry[] newArray(int size) {
            return new DataItemEntry[size];
        }
    };
}