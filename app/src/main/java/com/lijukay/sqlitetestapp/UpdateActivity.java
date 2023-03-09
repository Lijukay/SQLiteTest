package com.lijukay.sqlitetestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

public class UpdateActivity extends AppCompatActivity {

    TextInputLayout qwotable_til, qwotable_author_til, qwotable_found_in_til;
    EditText qwotable_et, qwotable_author_et, qwotable_found_in_et;
    Button update, delete;

    String id, qwotable, author, found_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        qwotable_til = findViewById(R.id.qwotable_tf_u);
        qwotable_author_til = findViewById(R.id.author_tf_u);
        qwotable_found_in_til = findViewById(R.id.found_in_tf_u);

        qwotable_et = qwotable_til.getEditText();
        qwotable_author_et = qwotable_author_til.getEditText();
        qwotable_found_in_et = qwotable_found_in_til.getEditText();

        getAndSetIntentData();

        update = findViewById(R.id.button_update);
        update.setOnClickListener(v -> {
            MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(UpdateActivity.this);
            myDatabaseHelper.updateData(id, qwotable_et.getText().toString().trim(), qwotable_author_et.getText().toString().trim(), qwotable_found_in_et.getText().toString().trim());
        });

        delete = findViewById(R.id.button_delete);
        delete.setOnClickListener(v -> confirmDialog());

    }

    void getAndSetIntentData(){
        if (getIntent().hasExtra("id") && getIntent().hasExtra("Qwotable") && getIntent().hasExtra("Author") && getIntent().hasExtra("Found in")){
            id = getIntent().getStringExtra("id");
            qwotable = getIntent().getStringExtra("Qwotable");
            author = getIntent().getStringExtra("Author");
            found_in = getIntent().getStringExtra("Found in");

            qwotable_et.setText(qwotable);
            qwotable_author_et.setText(author);
            qwotable_found_in_et.setText(found_in);
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        new MaterialAlertDialogBuilder(this, com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered)
                .setTitle("Delete item?")
                .setMessage("You are about to delete following item:\n\n" + qwotable)
                .setPositiveButton("Delete", (dialog, which) -> {
                    MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(UpdateActivity.this);
                    myDatabaseHelper.deleteOneRow(id);
                    finish();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

}