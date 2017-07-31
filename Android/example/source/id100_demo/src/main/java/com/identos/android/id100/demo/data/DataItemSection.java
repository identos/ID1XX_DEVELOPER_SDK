package com.identos.android.id100.demo.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

class DataItemSection extends DataItem implements Parcelable {
    DataItemSection(String title) {
        super(title);
    }

    @Override
    boolean isSection() {
        return true;
    }

    private DataItemSection(Parcel in) {
        super(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public static final Creator<DataItemSection> CREATOR = new Parcelable.Creator<DataItemSection>() {
        @Override
        public DataItemSection createFromParcel(Parcel in) {
            return new DataItemSection(in);
        }

        @Override
        public DataItemSection[] newArray(int size) {
            return new DataItemSection[size];
        }
    };
}
