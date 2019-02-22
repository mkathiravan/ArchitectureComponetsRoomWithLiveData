package com.example.abacus.architecturecomponets;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Book.class}, version = 1)
public abstract class BookDatabase extends RoomDatabase {

    private static BookDatabase instance;

    public abstract BookDao bookDao();

    public static synchronized BookDatabase getInstance(Context context)
    {
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BookDatabase.class,"book_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private BookDao bookDao;

        private PopulateDbAsyncTask(BookDatabase db)
        {
            bookDao = db.bookDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            bookDao.insert(new Book("Book1","Carl Marx",1));
            bookDao.insert(new Book("Book2","Chegvara",2));
            bookDao.insert(new Book("Book3","Vijayan",3));
            return null;
        }
    }
}
