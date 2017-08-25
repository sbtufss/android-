// IBookManager.aidl
package com.example.sbtufss.myapplication;

// Declare any non-default types here with import statements
import com.example.sbtufss.myapplication.Book;

interface IBookManager {
    boolean add(in Book book);
    Book getFirstBook();
}
