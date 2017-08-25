这是有关于android远程服务的调用，要注意的是同一module中的aidl如何对应的java类必须要包名一样，
自定义数据类型要实现Parcelable，并且对于的aidl文件应该这样子比喻，我自定义一个Book类，那么book.aidl应该这样子写
// Book.aidl
package com.example.sbtufss.myapplication;

// Declare any non-default types here with import statements

parcelable Book;
而Book.java,注意Book.aidl和Book.java之间的包名，必须一样
package com.example.sbtufss.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sbtufss on 17/8/25.
 */

public class Book implements Parcelable {

而我们在使用我们自定义的类时，应该这样子使用
// IBookManager.aidl
package com.example.sbtufss.myapplication;

// Declare any non-default types here with import statements
import com.example.sbtufss.myapplication.Book;

interface IBookManager {
    boolean add(in Book book);
    Book getFirstBook();
}

服务类
package com.example.sbtufss.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;


import java.util.ArrayList;

/**
 * Created by sbtufss on 17/8/25.
 */

public class RemetoService extends Service {
    private ArrayList<Book> books=new ArrayList<>();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("远程服务被绑定了");
        return new RemetoBinder();
    }

    class RemetoBinder extends IBookManager.Stub{

        @Override
        public boolean add(com.example.sbtufss.myapplication.Book book) throws RemoteException {
            return books.add(book);
        }

        @Override
        public Book getFirstBook() throws RemoteException {
            return books.get(0);
        }
    }
}

而我们在调用远程服务工程的类应该怎么做
直接copy远程服务工程的aidl到调用服务工程main包下，同时我们的自定义类型Book.java必须要跟aidl对应的Book.aidl相对应
public class MainActivity extends AppCompatActivity {

    private MyConn conn;
    private IBookManager iBookManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void bindRetemoService(View view){
        conn=new MyConn();
        Intent intent=new Intent();
        //intent.setAction("com.example.sbtufss.myapplication.RemetoService");
        intent.setComponent(new     ComponentName("com.example.sbtufss.myapplication","com.example.sbtufss.myapplication.RemetoService"));
        bindService(intent,conn, Context.BIND_AUTO_CREATE);
    }

    class MyConn implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iBookManager=IBookManager.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }

    public void add(View view)  {
        try {
            iBookManager.add(new Book("lisi",30));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void get(View view){
        try {
            Book book=iBookManager.getFirstBook();
            System.out.println(book);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

其中的intent.setComponent(new ComponentName("com.example.sbtufss.myapplication","com.example.sbtufss.myapplication.RemetoService"));中的包名com.example.sbtufss.myapplication是对应服务工程的包名，而com.example.sbtufss.myapplication.RemetoService这个类名是服务类在服务工程所对应的类名，意思就是我根据服务类所在包名找到服务工程再根据服务类的类名来找到我们的远程服务
