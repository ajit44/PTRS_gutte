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

public class HomeTab_3 extends Fragment{




    ListView listViewmedical;
    private static final String TAG_RESULTS = "result";
    JSONArray peoples = null;
    public static int cnt, cnt1;

    public static String myJSON;
    /*tab 2*/  public static String DoctorID_Temp, PrescriptionID_Temp, PrescPicture_Temp, Date_Temp, D_Last_name_Temp, D_first_name_Temp, D_mid_name_Temp;
    public static String DoctorID[],PrescriptionID[],PrescPicture[],Date[],D_Last_name[],D_first_name[],D_mid_name[];
    private ProgressDialog progress;
    final Context context = getActivity();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_tab3, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listViewmedical = (ListView)view.findViewById(R.id.listViewmedical);
        try { getData();
            listViewmedical.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                  //  TextView textView = (TextView) v.findViewById(R.id.fname);
                  //  send_ditails= textView.getText().toString();
                    DoctorID_Temp =  DoctorID[position];
                    PrescriptionID_Temp = PrescriptionID[position];
                    PrescPicture_Temp =  PrescPicture[position];
                    Date_Temp =  Date[position];
                    D_Last_name_Temp = D_Last_name[position];
                    D_first_name_Temp =  D_first_name[position];
                    D_mid_name_Temp =  D_mid_name[position];



                     Intent s = new Intent(getActivity(),HomeTab_3_medicalDitails.class);
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


                    httppost = new HttpPost("http://192.168.0.3/MRTS/patient/" + "medical_priscription.php?p_id=" + "11223344");

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

                DoctorID = new String[cnt];
                PrescriptionID = new String[cnt];
                PrescPicture = new String[cnt];
                Date = new String[cnt];
                D_Last_name = new String[cnt];
                D_first_name = new String[cnt];
                D_mid_name = new String[cnt];




            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);

                //descriptionType[cnt1]=c.getString("descriptionType");


                    DoctorID[cnt1] = c.getString("DoctorID");
                    PrescriptionID[cnt1] = c.getString("PrescriptionID");
                    PrescPicture[cnt1] = c.getString("PrescPicture");
                    Date[cnt1] = c.getString("Date");
                    D_Last_name[cnt1] = c.getString("D_Last_name");
                    D_first_name[cnt1] = c.getString("D_first_name");
                    D_mid_name[cnt1] = c.getString("D_mid_name");

                cnt1++;
            }
            // setTitle(patientNameF[0]+" "+patientNameL[0]);
          //  setTitle("sfse1");

           
                layout_medical_priscrip r = new layout_medical_priscrip(getActivity(), DoctorID,PrescriptionID, PrescPicture,Date,D_Last_name,D_first_name,D_mid_name);
                listViewmedical.setAdapter(r);



            if(cnt==0) {
                Toast.makeText(getActivity(), "No Products Available", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
