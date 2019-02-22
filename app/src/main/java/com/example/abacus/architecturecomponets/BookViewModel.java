package com.example.abacus.architecturecomponets;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class BookViewModel extends AndroidViewModel {

    private BookRepository repository;
    private LiveData<List<Book>> allBooks;

    public BookViewModel(@NonNull Application application) {
        super(application);
        repository = new BookRepository(application);
        allBooks = repository.getAllBooks();
    }

    public void insert(Book book)
    {
        repository.insert(book);
    }

    public void update(Book book)
    {
        repository.update(book);
    }

    public void delete(Book book)
    {
        repository.delete(book);
    }

    public void deleteAllNotes()
    {
        repository.deleteAllBooks();
    }

    public LiveData<List<Book>> getAllBooks() {
        return allBooks;
    }
}
