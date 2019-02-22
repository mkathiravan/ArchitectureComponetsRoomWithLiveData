package com.example.abacus.architecturecomponets;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    void insert(Book book);

    @Update
    void update(Book book);

    @Delete
    void delete(Book book);

    @Query("DELETE FROM BOOK_TABLE")
    void deleteAllBooks();


    @Query("SELECT * FROM BOOK_TABLE ORDER BY priority DESC")
    LiveData<List<Book>>  getAllBooks();
}
