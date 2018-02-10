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

import com.stfalcon.frescoimageviewer.ImageViewer;

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

    private ImageOverlayView overlayView;

    ListView listViewCasePaper;
    private static final String TAG_RESULTS = "result";
    JSONArray peoples = null;
    public static int cnt, cnt1;

    public static String myJSON;
    /*tab 1*/  public static String doc_name_Temp,d_date_Temp;
    public static String doc_name[],d_date[],action[],descriptions[],posters[];
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
                    d_date_Temp =  d_date[position];
                    doc_name_Temp = doc_name[position];


                    showPicker(position);
                    // Intent s = new Intent(getActivity(),HomeTab_1_CasePaperDitails.class);
                  //  startActivity(s);
                }
            });
        }catch (Exception s){
            Toast.makeText(getActivity(), "listClick : "+s, Toast.LENGTH_SHORT).show();
        }
    }

    protected void showPicker(int startPosition) {
        ImageViewer.Builder builder = new ImageViewer.Builder<>(context, posters)
                //  new ImageViewer.Builder<>(this, doc_name)
                .setStartPosition(startPosition)
                .setImageChangeListener(getImageChangeListener())


                .setContainerPadding(context, R.dimen.padding)
                .setImageMargin(context, R.dimen.image_margin)

                .setOverlayView(overlayView);


        if (1==1) {
            //  overlayView = new ImageOverlayView(this);
            overlayView =new ImageOverlayView(context);
            builder.setOverlayView(overlayView);
            builder.setImageChangeListener(getImageChangeListener());
        }
        builder.show();



    }

    private ImageViewer.OnImageChangeListener getImageChangeListener() {
        return new ImageViewer.OnImageChangeListener() {
            @Override
            public void onImageChange(int position) {
                String url = posters[position];
                overlayView.setShareText(url);
                overlayView.setDescription(descriptions[position]);
            }
        };
    }



    //#######################################################################################3 grt data $$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            HttpPost httppost;
            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());


                        httppost = new HttpPost(URL.url+ "uploaded_doc.php?p_id=" +URL.static_Aadharno);

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
                    Toast.makeText(context, "mJ "+myJSON, Toast.LENGTH_SHORT).show();
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

                d_date = new String[cnt];
                doc_name = new String[cnt];
                action = new String[cnt];
            descriptions= new String[cnt];
            posters= new String[cnt];



            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);

                //doc_name[cnt1]=c.getString("doc_name");

                    d_date[cnt1] = c.getString("d_date");
                    doc_name[cnt1] = c.getString("doc_name");
                    action[cnt1] = c.getString("action");
                descriptions[cnt1] = "Date : "+d_date[cnt1]+"\nName : "+doc_name[cnt1];
                posters[cnt1] =URL.url+"Documents/"+URL.static_Aadharno+"/"+doc_name[cnt1]+".jpg";

                cnt1++;
            }
            // setTitle(patientNameF[0]+" "+patientNameL[0]);
          //  setTitle("sfse1");


                layout_casepaper a = new layout_casepaper(getActivity(), doc_name,d_date,action,posters);
                listViewCasePaper.setAdapter(a);


            if(cnt==0) {
                Toast.makeText(getActivity(), "No Products Available", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
