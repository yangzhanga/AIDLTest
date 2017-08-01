package com.example.zhangyang.aidl;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aidl.BinderPool.BinderPoolActivity;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    private IRemoteService remoteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        intent.setClass(this, RemoteService.class);
        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
        Button btn= (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(MainActivity.this, BinderPoolActivity.class);
                startActivity(intent1);
            }
        });
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            remoteService = IRemoteService.Stub.asInterface(service);
            try {
                Log.e(TAG, "Client pid=" + Process.myPid());
                Log.e(TAG, "RemoteService pid" + remoteService.getPid());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "Service断开连接");
            remoteService = null;
        }
    };

    @Override
    protected void onDestroy() {

        unbindService(serviceConnection);
        super.onDestroy();
    }
}
