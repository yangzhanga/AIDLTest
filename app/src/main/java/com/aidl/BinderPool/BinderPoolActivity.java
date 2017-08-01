package com.aidl.BinderPool;

import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.zhangyang.aidl.ICompute;
import com.example.zhangyang.aidl.ISecurityCenter;
import com.example.zhangyang.aidl.R;

/**
 * Created by zhangyang on 2017/7/31.
 */

public class BinderPoolActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    ISecurityCenter securityCenter;
    ICompute compute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.binderpool);


        new Thread(new Runnable() {
            @Override
            public void run() {

                doWork();
            }
        }).start();

    }

    private void doWork() {

        BinderPool binderPool = BinderPool.getInstance(BinderPoolActivity.this);
        IBinder binderSECURITY=binderPool.queryBinder(BinderPool.BINDER_SECURITY_CODE);
         securityCenter = SecurityCenterImpl.asInterface(binderSECURITY);
        Log.e(TAG,"SecurityCenterImpl");
        String msg="安卓";
        Log.e(TAG,msg);
        try {
            String password=securityCenter.encrypt(msg);
            Log.e("encrypt",password);
            Log.e("decrypt",securityCenter.decrypt(password));

        }catch (Exception e){
            e.printStackTrace();
        }

        Log.e(TAG,"ComputeImpl");
        IBinder binderCompute = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        compute = ComputeImpl.asInterface(binderCompute);
        try {
            Log.e("compute 1+3=",compute.add(1,3)+"");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
