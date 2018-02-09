package com.ajdeveloper.ptrs_gutte;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ajits on 28-12-2017.
 */

public class HomeTab_2 extends Fragment{




    ListView listViewReport;
    private static final String TAG_RESULTS = "result";
    JSONArray peoples = null;
    public static int cnt, cnt1;

    public static String myJSON;
    /*tab 2*/  static String reportDoctorID_Temp, reportLabLD_Temp, reportLabName_Temp, reportType_Temp, reportName_Temp, reportDescription_Temp, reportPic_Temp, reportDate_Temp, reportDoctorNameL_Temp, reportDoctorNameF_Temp, reportDoctorNameM_Temp;
    public static String reportDoctorID[],reportLabLD[],reportLabName[],reportType[],reportName[],reportDescription[],reportPic[],reportDate[],reportDoctorNameL[],reportDoctorNameF[],reportDoctorNameM[];
    private ProgressDialog progress;
    final Context context = getActivity();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_tab2, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listViewReport = (ListView)view.findViewById(R.id.listViewReport);

        getData();



        try {

            listViewReport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    //  TextView textView = (TextView) v.findViewById(R.id.fname);
                    //  send_ditails= textView.getText().toString();
                    reportDoctorID_Temp =  reportDoctorID[position];
                    reportLabLD_Temp = reportLabLD[position];
                    reportLabName_Temp =  reportLabName[position];
                    reportType_Temp =  reportType[position];
                    reportName_Temp = reportName[position];
                    reportDescription_Temp =  reportDescription[position];
                    reportPic_Temp =  reportPic[position];
                    reportDate_Temp =  reportDate[position];
                    reportDoctorNameL_Temp =   reportDoctorNameL[position];
                    reportDoctorNameF_Temp =   reportDoctorNameF[position];
                    reportDoctorNameM_Temp = reportDoctorNameM[position];



                    Intent s = new Intent(getActivity(),HomeTab2_reportsDitails.class);
                    startActivity(s);
                }
            });
        }catch (Exception s){
            Toast.makeText(getActivity(), "listClick : "+s, Toast.LENGTH_SHORT).show();

    }
    }




    //#######################################################################################3 grt data $$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            HttpPost httppost;
            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());

                        httppost = new HttpPost("http://192.168.0.3/MRTS/patient/"+ "reports.php?p_id=" +"11223344");

                // Depends on your web service
                httppost.setHeader("Content-type", "application/json");

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();

                } catch (Exception e) {
                    // Oops

                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                try {

                    myJSON=result;
                    showList();

                }catch (Exception f){

                    Toast.makeText(getActivity(), "error recive ::"+f, Toast.LENGTH_SHORT).show();
                }
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }
    protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);
            cnt=0;
            cnt1=0;


            for(int i=0;i<peoples.length();i++){
                cnt++;
            }

                reportDoctorID = new String[cnt];
                reportLabLD = new String[cnt];
                reportLabName = new String[cnt];
                reportType = new String[cnt];
                reportName = new String[cnt];
                reportDescription = new String[cnt];
                reportPic = new String[cnt];
                reportDate = new String[cnt];
                reportDoctorNameL = new String[cnt];
                reportDoctorNameF = new String[cnt];
                reportDoctorNameM = new String[cnt];




            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);

                //descriptionType[cnt1]=c.getString("descriptionType");


                    reportDoctorID[cnt1] = c.getString("DoctorID");
                    reportLabLD[cnt1] = c.getString("LabID");
                    reportLabName[cnt1] = c.getString("LabName");
                    reportType[cnt1] = c.getString("ReportType");
                    reportName[cnt1] = c.getString("ReportName");
                    reportDescription[cnt1] = c.getString("Description");
                    reportPic[cnt1] = c.getString("Pic");
                    reportDate[cnt1] = c.getString("Date");
                    reportDoctorNameL[cnt1] = c.getString("D_Last_name");
                    reportDoctorNameF[cnt1] = c.getString("D_first_name");
                    reportDoctorNameM[cnt1] = c.getString("D_mid_name");


                cnt1++;
            }
            // setTitle(patientNameF[0]+" "+patientNameL[0]);
          //  setTitle("sfse1");


                layout_reports r = new layout_reports(getActivity(), reportType, reportDate,reportName, reportDoctorNameL, reportDoctorNameF, reportDoctorNameM, reportLabName);
                listViewReport.setAdapter(r);



            if(cnt==0) {
                Toast.makeText(getActivity(), "No Products Available", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
