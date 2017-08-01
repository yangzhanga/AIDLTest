package diy;

/**
 * Created by zhangyang on 2017/7/31.
 * 手写AIDL相关代码
 */

public interface IRemote extends android.os.IInterface{
    static final String DESCRIPT="diy";
    static final int TRANSACTION_getPid = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);

    int getPid() throws android.os.RemoteException;
}
