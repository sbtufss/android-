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
