package com.example.pillreminder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class EditSingleRecordActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText name, dosage, startdate, enddate, time;
    Button update;
    Spinner spinner;
    Cursor cursor;
    String SQLiteDataBaseQueryHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String IDholder;
    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_single_record);

        name = findViewById(R.id.set_Name);
        startdate = findViewById(R.id.set_date);
        enddate = findViewById(R.id.set_date2);
        dosage = findViewById(R.id.set_dosage);
        time = findViewById(R.id.set_time);
        spinner = findViewById(R.id.spinner);
        update = findViewById(R.id.donebutton);
        sqLiteHelper = new SQLiteHelper(this);


        startdate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                handleDateButton();
            }
        });

        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDatetwoButton();
            }


        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTimeButton();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String AddTime1 = time.getText().toString().trim();
                String TextStartDate1 = startdate.getText().toString().trim();
                String TextEndDate1 = enddate.getText().toString().trim();
                String TextDosage1 = dosage.getText().toString().trim();
                String TextName1 = name.getText().toString().trim();
                String getspinner = spinner.getSelectedItem().toString().trim();

                OpenSQliteDataBase();
                SQLiteDataBaseQueryHolder = "UPDATE " + SQLiteHelper.TABLE_NAME + " SET " + SQLiteHelper.Table_Column_1_Name + " = '" + TextName1 + "' , " +
                        "" + SQLiteHelper.Table_Column_2_Dosage + " = '" + TextDosage1 + "' , " +
                        "" + SQLiteHelper.Table_Column_3_startdate + " = '" + TextStartDate1 + "' , " + SQLiteHelper.Table_Column_4_enddate + " = '" + TextEndDate1 + "', "
                        + SQLiteHelper.Table_Column_5_time + " = '" + AddTime1 + "', " + SQLiteHelper.Table_Column_6_frequency + " = '" + getspinner + "' WHERE id = " + IDholder + "";


                sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

                sqLiteDatabase.close();


                Toast.makeText(EditSingleRecordActivity.this, "Data Edited Successfully", Toast.LENGTH_LONG).show();


                Intent intent = new Intent(EditSingleRecordActivity.this,MainActivity.class);
                startActivity(intent);




            }
        });

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.Items,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    protected void onResume() {

        ShowSRecordInEditText();

        super.onResume();
    }

    public void ShowSRecordInEditText() {

        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        IDholder = getIntent().getStringExtra("EditID");

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE_NAME + " WHERE id = " + IDholder + "", null);

        if (cursor.moveToFirst()) {

            do {
                name.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_1_Name)));

                dosage.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_2_Dosage)));
               startdate.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_startdate)));
                enddate.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_4_enddate)));
                time.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_5_time)));


            }
            while (cursor.moveToNext());

            cursor.close();
        }


    }
    public void OpenSQliteDataBase () {
        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void handleDatetwoButton() {
        Calendar calender  = Calendar.getInstance();

        int YEAR = calender.get(Calendar.YEAR);
        int MONTH = calender.get(Calendar.MONTH);
        int DATE = calender.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {


                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR,year);
                calendar1.set(Calendar.MONTH,month);
                calendar1.set(Calendar.DATE,date);

                CharSequence dateCharSequence = DateFormat.format("MMM d,yyyy",calendar1);
               enddate.setText(dateCharSequence);

            }
        },YEAR,MONTH,DATE);
        datePickerDialog.show();
    }

    private void handleTimeButton() {
        Calendar calender = Calendar.getInstance();
        int HOUR = calender.get(Calendar.HOUR);
        int MINUTE = calender.get(Calendar.MINUTE);

        boolean is24HourFormat = DateFormat.is24HourFormat(this);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR,hour);
                calendar1.set(Calendar.MINUTE,minute);

                CharSequence CharSequence = DateFormat.format("hh:mm a",calendar1);
                time.setText(CharSequence);





            }
        },HOUR,MINUTE,is24HourFormat);
        timePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void handleDateButton() {
        Calendar calender  = Calendar.getInstance();

        int YEAR = calender.get(Calendar.YEAR);
        int MONTH = calender.get(Calendar.MONTH);
        int DATE = calender.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {


                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR,year);
                calendar1.set(Calendar.MONTH,month);
                calendar1.set(Calendar.DATE,date);

                CharSequence dateCharSequence = DateFormat.format("MMM d,yyyy",calendar1);
                startdate.setText(dateCharSequence);

            }
        },YEAR,MONTH,DATE);
        datePickerDialog.show();

    }
}
