package com.example.pillreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowSingleRecordActivity extends AppCompatActivity {
    String IDholder;
    TextView name,date,date2,dosage,time,frequency;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    Button delete,edit;
    String SQLiteDataBaseQueryHolder;
    SQLiteDatabase sqLiteDatabase;
    SQLiteDatabase sqLiteDatabaseObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_record);

        name = findViewById(R.id.set_Name);
        date = findViewById(R.id.set_date);
        date2 = findViewById(R.id.set_date2);
        dosage = findViewById(R.id.set_dosage);
        time = findViewById(R.id.set_time);
        frequency = findViewById(R.id.spinner);
        delete = findViewById(R.id.deletebutton);
        edit = findViewById(R.id.editbutton);
        sqLiteHelper = new SQLiteHelper(this);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSQLiteDataBase();
                SQLiteDataBaseQueryHolder = "DELETE FROM "+SQLiteHelper.TABLE_NAME+" WHERE id = "+IDholder+"";

                sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);

                sqLiteDatabase.close();

                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),EditSingleRecordActivity.class);
                intent.putExtra("EditID",IDholder);
                startActivity(intent);
            }
        });

    }
    
    protected void onResume(){
        ShowSingleRecordInTextView();
        super.onResume();
    }

    public void ShowSingleRecordInTextView() {
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        IDholder = getIntent().getStringExtra("ListViewClickedItemValue");
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE_NAME + " WHERE id = " + IDholder + "", null);

        if (cursor.moveToFirst()){
            do{
               name.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_1_Name)));

                dosage.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_2_Dosage)));
                date.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_startdate)));
                date2.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_4_enddate)));
                time.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_5_time)));
                frequency.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_6_frequency)));

            }
            while (cursor.moveToNext());
            cursor.close();
        }
    }

    public void OpenSQLiteDataBase() {
        sqLiteDatabaseObj=openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE,null);
    }
}
