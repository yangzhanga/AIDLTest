package com.aidl.BinderPool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.zhangyang.aidl.IBinderPool;

import java.util.concurrent.CountDownLatch;

/**
 * Created by zhangyang on 2017/7/31.
 */

public class BinderPool {
    private static final String TAG="BinderPool";
    public static final int BINDER_NONE=-1;
    public static final int BINDER_SECURITY_CODE=0;
    public static final int BINDER_COMPUTE=1;

    private Context mContext;
    private IBinderPool mIbinderPool;
    private static volatile BinderPool sInstance;
    private CountDownLatch mConnectBinderPoolCountDownLatch;


    public BinderPool(Context context) {
        mContext = context.getApplicationContext();
        connectBinderPoolService();
    }

    public static BinderPool getInstance(Context context){

        if (sInstance==null){
            synchronized (BinderPool.class){
                if (sInstance==null){
                    sInstance=new BinderPool(context);
                }
            }
        }

        return sInstance;
    }

    public IBinder queryBinder(int code){
        IBinder iBinder=null;
        try {
            if (mIbinderPool!=null){
                iBinder=mIbinderPool.queryBinder(code);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return iBinder;
    }

    private synchronized void connectBinderPoolService(){
        mConnectBinderPoolCountDownLatch=new CountDownLatch(1);
        Intent intent=new Intent(mContext,BinderPoolService.class);
        mContext.bindService(intent,connection,Context.BIND_AUTO_CREATE);

        try {
            mConnectBinderPoolCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIbinderPool=IBinderPool.Stub.asInterface(service);
            try {
                mIbinderPool.asBinder().linkToDeath(mBinderPoolDeathRecipient,0);
            }catch (Exception e){
                e.printStackTrace();
            }
            mConnectBinderPoolCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

   private IBinder.DeathRecipient mBinderPoolDeathRecipient=new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.e(TAG,"Binder died");
            mIbinderPool.asBinder().unlinkToDeath(mBinderPoolDeathRecipient,0);
            mIbinderPool = null;
            connectBinderPoolService();
        }
    };

    public static class BinderPoolImpl extends IBinderPool.Stub{

        public BinderPoolImpl() {
            super();
        }

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder iBinder=null;
            switch (binderCode){
                case BINDER_SECURITY_CODE:

                    iBinder=new SecurityCenterImpl();
                    break;

                case BINDER_COMPUTE:

                    iBinder=new ComputeImpl();
                    break;

                default:
                    break;

            }
            return iBinder;
        }
    }
}
