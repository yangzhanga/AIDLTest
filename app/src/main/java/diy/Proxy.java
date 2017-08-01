package diy;

import android.os.IBinder;
import android.os.RemoteException;

/**
 * Created by zhangyang on 2017/7/31.
 * 代表远程进程的Binder对象的本地代理，继承自IBinder，因而具有跨进程传输的能力。
 * 实际上，在跨越进程的时候，Binder驱动会自动完成代理对象和本地对象的转换。
 */

public class Proxy implements IRemote {

    private IBinder mRemote;

    public Proxy(IBinder mRemote) {
        this.mRemote = mRemote;
    }

    public java.lang.String getInterfaceDescriptor()
    {
        return DESCRIPT;
    }


    @Override
    public int getPid() throws RemoteException {

        android.os.Parcel _data=android.os.Parcel.obtain();
        android.os.Parcel _reply=android.os.Parcel.obtain();
        int _result;
        try {
            _data.writeInterfaceToken(DESCRIPT);
            mRemote.transact(TRANSACTION_getPid,_data,_reply,0);
            _reply.readException();
            _result=_reply.readInt();
        }
        finally {
            _data.recycle();
            _reply.recycle();
        }

        return _result;
    }

    @Override
    public IBinder asBinder() {
        return mRemote;
    }
}
