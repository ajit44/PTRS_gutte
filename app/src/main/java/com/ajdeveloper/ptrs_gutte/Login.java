package com.ajdeveloper.ptrs_gutte;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Login extends AppCompatActivity {

    EditText Aadhar_OTP,OTP;
    TextView aadhar_label,aadhar_tv,edit_text_label;

    String receivedValue, clearChat = "no", delete,aadharno_send;
    final Context context = this;
    Random random;
    SmsManager smsManager;
    public static String myJSON,ID;
    private static final String TAG_RESULTS="result";
    JSONArray peoples = null;
    public  static  int cnt;
    String weburl="http://192.168.0.3/MRTS/";

    int flg_OTP_AAdhar=0;
    Button log_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        smsManager=SmsManager.getDefault();

         random = new Random();
        try {

            aadhar_label = (TextView)findViewById(R.id.aadhar_label);
            aadhar_tv= (TextView)findViewById(R.id.aadhar_tv);
            edit_text_label= (TextView)findViewById(R.id.edit_text_label);
            log_in = (Button) findViewById(R.id.log_in);
            Aadhar_OTP = (EditText) findViewById(R.id.Aahar_number);
            // OTP = (EditText) findViewById(R.id.otp_number);

            log_in.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Aadhar_OTP.getText().toString().equals("")){
                        Aadhar_OTP.setError("Enter Aadhar!");
                    }
                    else
                    {
                        aadharno_send=Aadhar_OTP.getText().toString();
                         if(flg_OTP_AAdhar==0)
                         {
                             getData();
                         }
                         else if(flg_OTP_AAdhar==1){

                              if(URL.OTP.equals(Aadhar_OTP.getText().toString())){
                               Intent s =new Intent(getApplicationContext(),Home.class);
                               startActivity(s);
                              }else{
                                  Toast.makeText(context, "FUCKKKKKKKKKKKKKKKK", Toast.LENGTH_SHORT).show();
                              }
                         }
                     
                    }
                }
            });
        }catch (Exception s){
            Toast.makeText(this, "onc +"+s, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
        
        
        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost= new HttpPost(weburl+"temp");

                try {
                    httppost= new HttpPost(URL.url+"login.php?Username="+aadharno_send);
                }catch (Exception s){
                    Toast.makeText(Login.this, "get 1 "+s, Toast.LENGTH_SHORT).show();
                }

                               // Depends on your web service
                try{
                    httppost.setHeader("Content-type", "application/json");
                }catch (Exception s){
                    Toast.makeText(Login.this, "get 1 "+s, Toast.LENGTH_SHORT).show();
                }
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

                try{


                    //  Toast.makeText(Login.this, result, Toast.LENGTH_SHORT).show();
                    if(result.contains("fail"))
                    {
                     //   Password.setError("Please Enter Correct Password");
                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        myJSON=result;
                         // Toast.makeText(Login.this, "Data : "+myJSON, Toast.LENGTH_SHORT).show();
                        showList();
                    }
                }catch (Exception s){
                    Toast.makeText(Login.this, "get 2 "+s, Toast.LENGTH_SHORT).show();
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
            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                cnt++;
            }
            if(cnt==0)
            {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            }
//static String FirstName,MiddleName,LastName,Email,patientNameSend_server,User_State,User_City;

            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);

                URL.Mobile_No=c.getString("phone_no");

                //edittext Changing...

                aadhar_label.setVisibility(View.VISIBLE);
                aadhar_tv.setVisibility(View.VISIBLE);
                aadhar_tv.setText(aadharno_send);
                URL.static_Aadharno=aadharno_send;
                edit_text_label.setText("Enter OTP");
                Drawable img = getApplicationContext().getResources().getDrawable( R.drawable.ic_lock_outline_black_24dp );
                img.setBounds( 0, 0, 60, 60 );
                Aadhar_OTP.setCompoundDrawables( img, null, null, null );
                edit_text_label.setText("Enter OTP");
                Aadhar_OTP.setText("");
                Aadhar_OTP.setHint("OTP");
                log_in.setText("Verify");
                flg_OTP_AAdhar=1;
               // Toast.makeText(context, "Mobile number get::"+URL.Mobile_No, Toast.LENGTH_SHORT).show();
                //send OTP
                try {
                    URL.OTP = String.format("%04d", random.nextInt(10000));
                 //   smsManager.sendTextMessage(URL.Mobile_No, null, URL.OTP, null, null);
                    Toast.makeText(context, "OTP Send: " + URL.OTP, Toast.LENGTH_SHORT).show();
                }catch (Exception e) {
                   // e.printStackTrace();
                    Toast.makeText(context, "ERr: " + e, Toast.LENGTH_SHORT).show();

                }

            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
      /*  switch (Type)
        {
            case "Doctor":

                Toast.makeText(this, "pa Doctor", Toast.LENGTH_SHORT).show();
                break;
            case  "Admin":
                Toast.makeText(this, "pa Admin", Toast.LENGTH_SHORT).show();

                //   i = new Intent(getApplicationContext(),Admin_Activity.class);
                //  startActivity(i);
                //  finish();
                break;
            case "Patient":
                i = new Intent(getApplicationContext(),Home.class);
                startActivity(i);
                finish();
                break;
        }*/
    }

}
