package com.example.abacus.architecturecomponets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditBookActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_DESCRIPTION = "extra_description";
    public static final String EXTRA_PRIORITY = "extra_priority";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);



        editTextTitle = (EditText)findViewById(R.id.edit_text_title);
        editTextDescription = (EditText)findViewById(R.id.edit_text_description);
        numberPickerPriority = (NumberPicker)findViewById(R.id.number_picker_priority);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID))
        {
            setTitle("Edit Note");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));
        }
        else
        {
            setTitle("Add Note");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
         menuInflater.inflate(R.menu.add_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.save_note:
                saveNote();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {

        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();

        if(title.trim().isEmpty() || description.trim().isEmpty())
        {
            Toast.makeText(AddEditBookActivity.this,"Please enter title and description",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TITLE,title);
        intent.putExtra(EXTRA_DESCRIPTION,description);
        intent.putExtra(EXTRA_PRIORITY,priority);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if(id != -1)
        {
            intent.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK,intent);
        finish();
    }


}
