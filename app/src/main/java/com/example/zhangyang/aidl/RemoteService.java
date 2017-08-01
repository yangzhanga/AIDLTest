package com.example.zhangyang.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by zhangyang on 2017/7/31.
 */

public class RemoteService extends Service {

    public RemoteService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    //Stub类：Binder的实现类，服务端需要实现这个类来提供服务。
    private final IRemoteService.Stub iBinder=new IRemoteService.Stub() {

        @Override
        public int getPid() throws RemoteException {
            return Process.myPid();
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };
}
