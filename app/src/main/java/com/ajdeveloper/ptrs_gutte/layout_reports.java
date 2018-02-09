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

public class layout_reports extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] reportType;
    private final String[] reportDate;
    private final String[] reportName;
    private final String[] doctorNameL;
    private final String[] doctorNameF;
    private final String[] doctorNameM;
    private final String[] labName;


   public layout_reports(Activity context, String[]reportType, String[]reportDate, String[]reportName, String[]doctorNameL, String[]doctorNameF, String[]doctorNameM, String[]labName){
        super(context, R.layout.layout_reports,reportType);

        this.context=context;
       this.reportType=reportType;
       this.reportDate=reportDate;
       this.reportName=reportName;
       this.doctorNameL=doctorNameL;
       this.doctorNameF=doctorNameF;
       this.doctorNameM=doctorNameM;
       this.labName=labName;



   }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.layout_reports, null, true);

        TextView report_TYPE=(TextView)rowView.findViewById(R.id.report_type);
        report_TYPE.setText(reportType[position]);

        TextView report_date=(TextView)rowView.findViewById(R.id.report_date);
        report_date.setText(reportDate[position]);

        TextView report_name=(TextView)rowView.findViewById(R.id.report_name);
        report_name.setText(reportName[position]);

        TextView doctor_name=(TextView)rowView.findViewById(R.id.doctor_name);
        doctor_name.setText(doctorNameL[position]+" "+doctorNameF[position]+" "+doctorNameM[position]+" ");

        TextView lab_name=(TextView)rowView.findViewById(R.id.Lab_name);
        lab_name.setText(labName[position]);





        return rowView;
    }
}
