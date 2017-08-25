package com.example.sbtufss.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sbtufss on 17/8/25.
 */

public class Book implements Parcelable {
    private String author;
    private int money;

    public Book(String author,int money){
        super();
        this.author=author;
        this.money=money;
    }

    protected Book(Parcel in) {
        this.author=in.readString();
        this.money=in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeInt(money);
    }
}
