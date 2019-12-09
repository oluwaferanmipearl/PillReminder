package com.example.pillreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import android.widget.ListView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {


    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
   ExampleAdapter listAdapter ;
    ListView LISTVIEW;

    ArrayList<String> ID_Array;
    ArrayList<String> NAME_Array;
    ArrayList<String> DOSAGE_Array;
    ArrayList<String> START_Array;
    ArrayList<String> END_Array;
    ArrayList<String> TIME_Array;
    ArrayList<String> FREQUENCY_Array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LISTVIEW = findViewById(R.id.recyclerView);

        ID_Array = new ArrayList<String>();

        NAME_Array = new ArrayList<String>();

        DOSAGE_Array = new ArrayList<String>();
        START_Array = new ArrayList<String>();
        END_Array = new ArrayList<String>();
        TIME_Array= new ArrayList<>();
        FREQUENCY_Array= new ArrayList<>();


        sqLiteHelper = new SQLiteHelper(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {

        ShowSQLiteDBdata() ;

        super.onResume();
    }

    private void ShowSQLiteDBdata() {

        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+SQLiteHelper.TABLE_NAME+"", null);

        ID_Array.clear();
        NAME_Array.clear();
        DOSAGE_Array.clear();
        START_Array.clear();
        END_Array.clear();
        TIME_Array.clear();
        FREQUENCY_Array.clear();

        if (cursor.moveToFirst()) {
            do {

                ID_Array.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_ID)));

                NAME_Array.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_1_Name)));

                DOSAGE_Array.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_2_Dosage)));
                START_Array.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_startdate)));
                END_Array.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_4_enddate)));
                TIME_Array.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_5_time)));
                FREQUENCY_Array.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_6_frequency)));


            } while (cursor.moveToNext());
        }

        listAdapter = new ExampleAdapter(MainActivity.this,

                ID_Array,
                NAME_Array,
               DOSAGE_Array,
                START_Array,
                END_Array,
                TIME_Array,
                FREQUENCY_Array

        );

        LISTVIEW.setAdapter(listAdapter);

        cursor.close();
    }
}