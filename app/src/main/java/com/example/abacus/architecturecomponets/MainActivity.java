package com.example.abacus.architecturecomponets;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    private BookViewModel bookViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_book);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddEditBookActivity.class);
                startActivityForResult(intent,ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final BookAdapter adatper = new BookAdapter();
        recyclerView.setAdapter(adatper);

        bookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        bookViewModel.getAllBooks().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(@Nullable List<Book> books) {

                //update RecyclerView

                adatper.setBooks(books);
            }
        });

        //If you want to use ListAdapter with update the list with the animation to can use the following snippet

//        bookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
//        bookViewModel.getAllBooks().observe(this, new Observer<List<Book>>() {
//            @Override
//            public void onChanged(@Nullable List<Book> books) {
//
//                //update RecyclerView
//
//                adatper.submitList(books);
//            }
//        });

        //Swipe the recyclerview Item to delete the row or item.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                bookViewModel.delete(adatper.getBookAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this,"Note Deleted",Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);


        adatper.setOnItemClickListener(new BookAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                Intent intent = new Intent(MainActivity.this,AddEditBookActivity.class);
                intent.putExtra(AddEditBookActivity.EXTRA_ID,book.getId());
                intent.putExtra(AddEditBookActivity.EXTRA_TITLE,book.getTitle());
                intent.putExtra(AddEditBookActivity.EXTRA_DESCRIPTION,book.getDescription());
                intent.putExtra(AddEditBookActivity.EXTRA_PRIORITY,book.getPriority());
                startActivityForResult(intent,EDIT_NOTE_REQUEST);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK)
        {
            String title = data.getStringExtra(AddEditBookActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditBookActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditBookActivity.EXTRA_PRIORITY,1);

            Book book = new Book(title,description,priority);
            bookViewModel.insert(book);
            Toast.makeText(MainActivity.this,"Book Saved",Toast.LENGTH_SHORT).show();
        }
        else if(requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK)
        {

            int id = data.getIntExtra(AddEditBookActivity.EXTRA_ID,-1);

            if(id == -1)
            {
                Toast.makeText(MainActivity.this,"Note can't be updated",Toast.LENGTH_SHORT).show();
                return;
            }


            String title = data.getStringExtra(AddEditBookActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditBookActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditBookActivity.EXTRA_PRIORITY,1);

            Book book = new Book(title,description,priority);
            book.setId(id);
            bookViewModel.update(book);
            Toast.makeText(MainActivity.this,"Note Updated",Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(MainActivity.this,"Book Not Saved",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.delete_all_notes:
                bookViewModel.deleteAllNotes();
                Toast.makeText(MainActivity.this,"Delete All Notes",Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
