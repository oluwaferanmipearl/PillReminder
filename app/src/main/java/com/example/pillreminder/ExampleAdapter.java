package com.example.pillreminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class ExampleAdapter extends BaseAdapter{
    Context context;
    ArrayList<String> ID;
    ArrayList<String> Name;
    ArrayList<String> Dosage;
    ArrayList<String> Date1;
    ArrayList<String> Date2;
    ArrayList<String> TimeA;
    ArrayList<String> FrequencyA;




    public ExampleAdapter(
            Context context2,
            ArrayList<String> id,
            ArrayList<String> name,
            ArrayList<String> dosage,
             ArrayList<String> date1,
                    ArrayList<String> date2,
                    ArrayList<String> time,
            ArrayList<String>frequency

    )
    {

        this.context = context2;
        this.ID = id;
        this.Name = name;
        this.Dosage = dosage;
        this.Date1=date1;
        this.Date2=date2;
        this.TimeA = time;
        this.FrequencyA = frequency;

    }

    public int getCount() {
        // TODO Auto-generated method stub
        return ID.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View child, ViewGroup parent) {

        Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            child = layoutInflater.inflate(R.layout.example_file, null);

            holder = new Holder();


            holder.Name_TextView =  child.findViewById(R.id.textView);
            holder.dosageTextView = child.findViewById(R.id.textview2);

            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }

        holder.Name_TextView.setText(Name.get(position));
        holder.dosageTextView.setText(Dosage.get(position));

        return child;
    }

    public class Holder {


        TextView Name_TextView;
        TextView dosageTextView;

    }

}