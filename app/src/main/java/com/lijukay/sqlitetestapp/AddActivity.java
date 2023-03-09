package com.lijukay.sqlitetestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddActivity extends AppCompatActivity {

    TextInputLayout qwotable_inputLayout, author_inputLayout, foundIn_inputLayout;
    EditText qwotable_input, author_input, foundIn_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        qwotable_inputLayout = findViewById(R.id.qwotable_tf);
        author_inputLayout = findViewById(R.id.author_tf);
        foundIn_inputLayout = findViewById(R.id.found_in_tf);

        qwotable_input = qwotable_inputLayout.getEditText();
        author_input = author_inputLayout.getEditText();
        foundIn_input = foundIn_inputLayout.getEditText();

        add_button = findViewById(R.id.button_add);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper mydb = new MyDatabaseHelper(AddActivity.this);
                mydb.addQwotable(qwotable_input.getText().toString().trim(),
                        author_input.getText().toString().trim(),
                        foundIn_input.getText().toString().trim()); //Add data to Database
            }
        });


    }
}