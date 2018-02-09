package com.ajdeveloper.ptrs_gutte;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by ajits on 17-12-2017.
 */

public class layout_casepaper extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] descriptionType;
    private final String[] descriptionDate;
    private final String[] doctorNameL;
    private final String[] doctorNameF;
    private final String[] doctorNameM;
    private final String[] hospitalName;


   public layout_casepaper(Activity context, String[]descriptionType, String[]descriptionDate, String[]doctorNameL, String[]doctorNameF, String[]doctorNameM, String[]hospitalName){
        super(context, R.layout.layout_casepaper,descriptionType);

        this.context=context;
       this.descriptionType=descriptionType;
       this.descriptionDate=descriptionDate;
       this.doctorNameL=doctorNameL;
       this.doctorNameF=doctorNameF;
       this.doctorNameM=doctorNameM;
       this.hospitalName=hospitalName;



   }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.layout_casepaper, null, true);

        TextView description_TYPE=(TextView)rowView.findViewById(R.id.description_type);
        description_TYPE.setText(descriptionType[position]);

        TextView description_date=(TextView)rowView.findViewById(R.id.description_date);
        description_date.setText(descriptionDate[position]);

        TextView doctor_name=(TextView)rowView.findViewById(R.id.doctor_name);
        doctor_name.setText(doctorNameL[position]+" "+doctorNameF[position]+" "+doctorNameM[position]+" ");

        TextView hospital_name=(TextView)rowView.findViewById(R.id.hospital_name);
        hospital_name.setText(hospitalName[position]);





        return rowView;
    }
}
