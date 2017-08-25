package com.example.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.example.myapplication.R;
import com.example.sbtufss.myapplication.Book;
import com.example.sbtufss.myapplication.IBookManager;


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
        intent.setComponent(new ComponentName("com.example.sbtufss.myapplication","com.example.sbtufss.myapplication.RemetoService"));
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
