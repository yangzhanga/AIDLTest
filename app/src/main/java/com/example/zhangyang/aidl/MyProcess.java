package com.example.zhangyang.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangyang on 2017/7/31.
 */

public class MyProcess implements Parcelable {
    public int pid;
    public String name;

    public MyProcess(int pid, String name) {
        this.pid = pid;
        this.name = name;
    }

    protected MyProcess(Parcel in) {
        pid = in.readInt();
        name = in.readString();
    }

    public static final Creator<MyProcess> CREATOR = new Creator<MyProcess>() {
        @Override
        public MyProcess createFromParcel(Parcel in) {
            return new MyProcess(in);
        }

        @Override
        public MyProcess[] newArray(int size) {
            return new MyProcess[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pid);
        dest.writeString(name);
    }
}
