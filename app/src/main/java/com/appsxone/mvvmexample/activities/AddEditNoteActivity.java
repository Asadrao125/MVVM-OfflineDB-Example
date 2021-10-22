package com.appsxone.mvvmexample.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.appsxone.mvvmexample.R;

public class AddEditNoteActivity extends AppCompatActivity {
    EditText edtTitle, edtDescription;
    NumberPicker numberPicker;
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String NUMBER = "number";
    public static final String ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        edtTitle = findViewById(R.id.edtTitle);
        edtDescription = findViewById(R.id.edtDesc);
        numberPicker = findViewById(R.id.number_picker);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if (intent.hasExtra(ID)) {
            setTitle("Edit Note");
            edtTitle.setText(getIntent().getStringExtra(TITLE));
            edtDescription.setText(getIntent().getStringExtra(DESCRIPTION));
            numberPicker.setValue(getIntent().getIntExtra(NUMBER, 1));
        } else {
            setTitle("Add Note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnSave:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = edtTitle.getText().toString().trim();
        String description = edtDescription.getText().toString().trim();
        int number = numberPicker.getValue();
        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please enter input fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(TITLE, title);
        intent.putExtra(DESCRIPTION, description);
        intent.putExtra(NUMBER, number);

        int id = getIntent().getIntExtra(ID, -1);
        if (id != -1) {
            intent.putExtra(ID, id);
        }

        setResult(RESULT_OK, intent);
        finish();
    }
}