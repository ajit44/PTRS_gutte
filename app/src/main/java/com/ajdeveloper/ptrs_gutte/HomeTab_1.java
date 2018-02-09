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

public class HomeTab_1 extends Fragment{


    ListView listViewCasePaper;
    private static final String TAG_RESULTS = "result";
    JSONArray peoples = null;
    public static int cnt, cnt1;

    public static String myJSON;
    /*tab 1*/  public static String descriptionType_Temp,description_Temp,descriptionDate_Temp,doctorNameL_Temp,doctorNameF_Temp,doctorNameM_Temp,hospitalName_Temp,casepaperPic_Temp,hospitalID_Temp,doctorID_Temp,patientPic_Temp,patientNameL_Temp,patientNameF_Temp,patientNameM_Temp,patientEmail_Temp;
    public static String descriptionType[],description[],descriptionDate[],doctorNameL[],doctorNameF[],doctorNameM[],hospitalName[],casepaperPic[],hospitalID[],doctorID[],patientPic[],patientNameL[],patientNameF[],patientNameM[],patientEmail[];
    private ProgressDialog progress;
     Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_tab1, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listViewCasePaper = (ListView) view.findViewById(R.id.listViewCasePaper);
        getData();



        try {
            context = getActivity();

            listViewCasePaper.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                  //  TextView textView = (TextView) v.findViewById(R.id.fname);
                  //  send_ditails= textView.getText().toString();
                    description_Temp =  description[position];
                    descriptionType_Temp = descriptionType[position];
                    doctorID_Temp =  doctorID[position];
                    hospitalID_Temp =  hospitalID[position];
                    casepaperPic_Temp = casepaperPic[position];
                    descriptionDate_Temp =  descriptionDate[position];
                    patientNameL_Temp =  patientNameL[position];
                    patientNameF_Temp =  patientNameF[position];
                    patientNameM_Temp =   patientNameM[position];
                    patientEmail_Temp =   patientEmail[position];
                    doctorNameL_Temp = doctorNameL[position];
                    doctorNameF_Temp = doctorNameF[position];
                    doctorNameM_Temp = doctorNameM[position];
                    hospitalName_Temp = hospitalName[position];


                    // Intent s = new Intent(getActivity(),HomeTab_1_CasePaperDitails.class);
                  //  startActivity(s);
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


                        httppost = new HttpPost("http://192.168.0.3/MRTS/patient/" + "casepaper.php?p_id=" +"11223344");

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
                    Toast.makeText(context, "dd", Toast.LENGTH_SHORT).show();
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

                description = new String[cnt];
                descriptionType = new String[cnt];
                doctorID = new String[cnt];
                hospitalID = new String[cnt];
                casepaperPic = new String[cnt];
                descriptionDate = new String[cnt];
                patientNameL = new String[cnt];
                patientNameF = new String[cnt];
                patientNameM = new String[cnt];
                patientEmail = new String[cnt];
                doctorNameL = new String[cnt];
                doctorNameF = new String[cnt];
                doctorNameM = new String[cnt];
                hospitalName = new String[cnt];



            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);

                //descriptionType[cnt1]=c.getString("descriptionType");

                    description[cnt1] = c.getString("Description");
                    descriptionType[cnt1] = c.getString("Type");
                    doctorID[cnt1] = c.getString("DoctorID");
                    hospitalID[cnt1] = c.getString("HosID");
                    casepaperPic[cnt1] = c.getString("Pic");
                    descriptionDate[cnt1] = c.getString("Date");
                    patientNameL[cnt1] = c.getString("P_last_name");
                    patientNameF[cnt1] = c.getString("P_mid_name");
                    patientNameM[cnt1] = c.getString("P_first_name");
                    patientEmail[cnt1] = c.getString("P_email");

                    doctorNameL[cnt1] = c.getString("D_Last_name");
                    doctorNameF[cnt1] = c.getString("D_first_name");
                    doctorNameM[cnt1] = c.getString("D_mid_name");
                    hospitalName[cnt1] = c.getString("Hos_Name");

                cnt1++;
            }
            // setTitle(patientNameF[0]+" "+patientNameL[0]);
          //  setTitle("sfse1");


                layout_casepaper a = new layout_casepaper(getActivity(), descriptionType, descriptionDate, doctorNameL, doctorNameF, doctorNameM, hospitalName);
                listViewCasePaper.setAdapter(a);


            if(cnt==0) {
                Toast.makeText(getActivity(), "No Products Available", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
