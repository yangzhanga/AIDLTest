package com.aidl.BinderPool;

import android.os.RemoteException;

import com.example.zhangyang.aidl.ICompute;

/**
 * Created by zhangyang on 2017/7/31.
 */

public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a+b;
    }
}
