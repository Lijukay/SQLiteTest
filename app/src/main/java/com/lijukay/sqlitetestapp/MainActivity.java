package com.lijukay.sqlitetestapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ExtendedFloatingActionButton add_button;

    MyDatabaseHelper myDatabaseHelper;
    ArrayList<String> id, qwotable, qwotable_author, qwotable_foundIn;

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.qwotableRV);
        add_button = findViewById(R.id.extended_fab);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        myDatabaseHelper = new MyDatabaseHelper(MainActivity.this);
        id = new ArrayList<>();
        qwotable = new ArrayList<>();
        qwotable_author = new ArrayList<>();
        qwotable_foundIn = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this, MainActivity.this, id, qwotable, qwotable_author, qwotable_foundIn);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = myDatabaseHelper.readAllData();
        if (cursor.getCount() == 0){
            findViewById(R.id.noData).setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                qwotable.add(cursor.getString(1)); //1 is the second column which is qwotable, 0 is the id column
                qwotable_author.add(cursor.getString(2)); //2 is the third column which is qwotable_author
                qwotable_foundIn.add(cursor.getString(3)); //3 is the fourth column which is qwotable_foundin
            }
            findViewById(R.id.noData).setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_all, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if (menuItem.getItemId() == R.id.delete){
            confirmDialog();
        }

        return super.onOptionsItemSelected(menuItem);
    }

    void confirmDialog(){
        new MaterialAlertDialogBuilder(this, com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered)
                .setTitle("Delete all data?")
                .setMessage("You are about to delete all data")
                .setPositiveButton("Delete", (dialog, which) -> {
                    Toast.makeText(this, "Delete All", Toast.LENGTH_SHORT).show();
                    MyDatabaseHelper db = new MyDatabaseHelper(MainActivity.this);
                    db.deleteAllData();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

}