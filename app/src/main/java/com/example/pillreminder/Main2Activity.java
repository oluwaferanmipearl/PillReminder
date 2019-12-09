package com.example.pillreminder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Date;
import java.util.Calendar;

public class Main2Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText TextStartDate;
    EditText AddTime;
    EditText TextEndDate;
    Button donebutton;
    Button deletebutton;
    EditText TextDosage;
    EditText TextName;
    SQLiteDatabase sqLiteDatabaseObj;
    boolean EditTextEmptyHold;
    String SQLiteDataBaseQueryHolder,Addtime1,Name1,Dosage1,Startdate1,Endate1;
    String timeholder,dosageholder,startdateholder,enddateholder,nameholder;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



         donebutton = findViewById(R.id.donebutton);
       deletebutton = findViewById(R.id.deletebutton);
         TextDosage = findViewById(R.id.set_dosage);
        TextName = findViewById(R.id.set_Name);
         TextStartDate = findViewById(R.id.set_date);
       TextEndDate = findViewById(R.id.set_date2);
        AddTime = findViewById(R.id.set_time);
        Spinner spinner =  findViewById(R.id.spinner);
        RelativeLayout date = findViewById(R.id.date);
        RelativeLayout date2 = findViewById(R.id.date2);
        RelativeLayout time = findViewById(R.id.time);


        TextDosage.addTextChangedListener(loginTextWatcher);
        TextName.addTextChangedListener(loginTextWatcher);
        TextStartDate.addTextChangedListener(loginTextWatcher);
        TextEndDate.addTextChangedListener(loginTextWatcher);
        AddTime.addTextChangedListener(loginTextWatcher);

        donebutton.setEnabled(false);

        donebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                        startActivity(intent);

               //the sqlite part
                SQliteDataBaseBuild();
                SQliteTableBuild();
                CheckEditTextStatus();
                InsertDataIntoSQLiteDatabase();
                // ends here
                //EmptyEditTextAfterDataInsert();

//                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
//                startActivity(intent);
            }
        });

        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextDosage.setText("");
                TextName.setText("");
                TextStartDate.setText("");
                AddTime.setText("");
                TextEndDate.setText("");

                cancelAlarm();

            }
        });



        TextStartDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
               handleDateButton();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                handleDateButton();
            }
        });



        AddTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTimeButton();

            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTimeButton();
            }
        });

        TextEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDatetwoButton();
            }
        });

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDatetwoButton();
            }
        });





        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.Items,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }
//the sqlite part
public void InsertDataIntoSQLiteDatabase(){

    if(EditTextEmptyHold == true)
    {
        SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelper.TABLE_NAME+" (name,dosage,startdate,enddate,time) VALUES('"+TextName.getText().toString()+"', '"+TextDosage.getText().toString()+"', '"+TextStartDate.getText().toString()+"', '"+TextEndDate.getText().toString()+"', '"+AddTime.getText().toString()+"');";

        sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);



        Toast.makeText(Main2Activity.this,"Data Inserted Successfully", Toast.LENGTH_LONG).show();

    }
    else {

        //Toast.makeText(Main2Activity.this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();

    }

}




    public void SQliteTableBuild() {
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS "+SQLiteHelper.TABLE_NAME+"("+SQLiteHelper.Table_Column_ID+" PRIMARY KEY AUTOINCREMENT NOT NULL, "+SQLiteHelper.Table_Column_1_Name+" VARCHAR, "+
                SQLiteHelper.Table_Column_2_Dosage+" VARCHAR,"+SQLiteHelper.Table_Column_3_startdate+" VARCHAR,"+SQLiteHelper.Table_Column_4_enddate+" VARCHAR,"+SQLiteHelper.Table_Column_5_time+" VARCHAR);");
    }

    public void SQliteDataBaseBuild() {
        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    private void EmptyEditTextAfterDataInsert() {
        TextDosage.setText("");
        TextName.setText("");
        TextStartDate.setText("");
        AddTime.setText("");
        TextEndDate.setText("");

    }

    // ends here

    public void CheckEditTextStatus() {
        String AddTime1 = AddTime.getText().toString().trim();
        String TextStartDate1 = TextStartDate.getText().toString().trim();
        String TextEndDate1 = TextEndDate.getText().toString().trim();
        String TextDosage1 =TextDosage.getText().toString().trim();
        String TextName1 = TextName.getText().toString().trim();
        if (TextUtils.isEmpty(AddTime1) ||TextUtils.isEmpty(TextDosage1) ||TextUtils.isEmpty(TextEndDate1) ||TextUtils.isEmpty(TextStartDate1) ||TextUtils.isEmpty(TextName1))
        {
            EditTextEmptyHold=false;
        }
        else{
            EditTextEmptyHold= true;
        }
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
                TextEndDate.setText(dateCharSequence);

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
               AddTime.setText(CharSequence);


               startAlarm(calendar1);


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
                  TextStartDate.setText(dateCharSequence);

            }
        },YEAR,MONTH,DATE);
        datePickerDialog.show();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String AddTime1 = AddTime.getText().toString().trim();
            String TextStartDate1 = TextStartDate.getText().toString().trim();
            String TextEndDate1 = TextEndDate.getText().toString().trim();
            String TextDosage1 =TextDosage.getText().toString().trim();
            String TextName1 = TextName.getText().toString().trim();


            donebutton.setEnabled(!AddTime1.isEmpty()&&!TextDosage1.isEmpty()&&!TextEndDate1.isEmpty()&&!TextName1.isEmpty()&&!TextStartDate1.isEmpty());

            if(AddTime1.isEmpty()){
                Toast.makeText(getApplicationContext(),"please,Fill up all fields",Toast.LENGTH_LONG).show();
            }
            else if(TextDosage1.isEmpty()){
                Toast.makeText(getApplicationContext(),"please,Fill up all fields",Toast.LENGTH_LONG).show();
            }
            else if(TextEndDate1.isEmpty()){
                Toast.makeText(getApplicationContext(),"please,Fill up all fields",Toast.LENGTH_LONG).show();
            }
            else if (TextName1.isEmpty()){
                Toast.makeText(getApplicationContext(),"please,Fill up all fields",Toast.LENGTH_LONG).show();
            }
            else if(TextStartDate1.isEmpty()){
                Toast.makeText(getApplicationContext(),"please,Fill up all fields",Toast.LENGTH_LONG).show();
            }
            else{}

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    private void startAlarm(Calendar calender1) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, alarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (calender1.before(Calendar.getInstance())) {
            calender1.add(Calendar.DATE, 1);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calender1.getTimeInMillis(), pendingIntent);
        }
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, alarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);

    }


}
