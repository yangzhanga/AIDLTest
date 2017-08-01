package diy;


/**
 * Created by zhangyang on 2017/7/31.
 * 一个Binder本地对象，它实现了IInterface接口，表明它具有远程Server承诺给Client的能力；
 * Stub是一个抽象类，具体的IInterface的相关实现需要我们手动完成。
 */

public  abstract class Stub extends android.os.Binder implements IRemote{
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
        this.attachInterface(this, DESCRIPT);
    }
    /**
     * Cast an IBinder object into a diy.IRemote interface,
     * generating a proxy if needed.
     */
    public static IRemote asInterface(android.os.IBinder obj)
    {
        if ((obj==null)) {
            return null;
        }
        android.os.IInterface iin = obj.queryLocalInterface(DESCRIPT);
        if (((iin!=null)&&(iin instanceof IRemote))) {
            return ((IRemote)iin);
        }
        return new Proxy(obj);
    }

    @Override
    public android.os.IBinder asBinder()
    {
        return this;
    }

    @Override
    public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
        switch (code)
        {
            case INTERFACE_TRANSACTION:
            {
                reply.writeString(DESCRIPT);
                return true;
            }
            case TRANSACTION_getPid:
            {
                data.enforceInterface(DESCRIPT);
                int _result = this.getPid();
                reply.writeNoException();
                reply.writeInt(_result);
                return true;
            }
        }
        return super.onTransact(code, data, reply, flags);
    }
}
