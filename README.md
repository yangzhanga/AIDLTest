# AIDLTest
## 创建步骤
### 首先，创建一个Service和一个AIDL(接口)文件，在AIDL接口中写重写的方法；接着，创建一个类继承自AIDL接口中的Stub类并实现Stub中的抽象方法；在Service的onBind方法中返回这个类的对象；然后客户端绑定服务端Service,建立连接后就可以访问远程服务端的方法。
### 在AIDL接口中有一个Proxy代理类和Stub抽象类，Proxy.java为代理类，提供给客户端使用的，通过Binder驱动和服务端通信。Stub.java为Binder实现类（Service端要实例化它并在onBind返回）
#### Proxy 
代表远程进程的Binder对象的本地代理，继承自IBinder，因而具有跨进程传输的能力。实际上，在跨越进程的时候，Binder驱动会自动完成代理对象和本地对象的转换。
#### Stub 
这个类继承了Binder, 说明它是一个Binder本地对象，它实现了IInterface接口，表明它具有远程Server承诺给Client的能力；Stub是一个抽象类，具体的IInterface的相关实现需要我们手动完成。

