package com.ajdeveloper.ptrs_gutte;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    CircleImageView profile_image;
    LinearLayout profile_clicked;
    TextView names;
//static String User_State="Maharashtra (MH)",User_City="Pune",patientNameSend_server="11223344";;

    public static String myJSON;
    static String PatientName_Temp,PatientContactNo,Location_Temp,Date_Temp,SenderName_Temp,SenderNo_Temp;
  static String[] PrimaryID,
            MemberID,
            Primary_EmergencyAction,
            Secondary_EmergencyAction,
            OtherSenderID,
            Location,
            Date,
            PrimaryLastName,
            PrimaryFirstName,
            PrimaryMiddleName,
            PrimaryContactNo,

            MemberLastName,
            MemberFirstName,
            MemberMiddleName,
            MemberContactNo,

            OtherLastName,
            OtherFirstName,
            OtherMiddleName,
            OtherContactNo;
    private ProgressDialog progress;

    Handler handler;

    private static final String TAG_RESULTS = "result";
    JSONArray peoples = null;
    public static int cnt, cnt1;
//Notificaton
public String m1="",m2="";
    public int xx=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        profile_clicked = (LinearLayout)hView.findViewById(R.id.profile_clicked) ;
        TextView name = (TextView)hView.findViewById(R.id.name);
        TextView email = (TextView)hView.findViewById(R.id.email);

        // TextView nav_user = (TextView)hView.findViewById(R.id.nav_doctor);
       // nav_user.setText(user);
     //  names =(TextView)findViewById(R.id.name) ;
        profile_image =(CircleImageView)hView.findViewById(R.id.profile_image);

      //  names.setText("dsf");
try {
   //.............. set data on profile
   // name.setText(State_and_City.FirstName+" "+State_and_City.MiddleName+" "+State_and_City.LastName);
  //  email.setText(State_and_City.Email);
   // new DownloadImageTask((ImageView) hView.findViewById(R.id.profile_image))
    //        .execute(State_and_City.url+"Images/"+State_and_City.patientNameSend_server + ".jpg");


    profile_clicked.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ///.......open profile activity
          //  Intent i = new Intent(getApplicationContext(), PatientProfile.class);
          //  startActivity(i);
        }
    });

    /*profile_clicked.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(), PatientProfile.class);
            startActivity(i);
        }
    });*/
}catch (Exception s){
    Toast.makeText(this, "sss "+s, Toast.LENGTH_SHORT).show();
}
      /*  handler = new Handler();
        Runnable runable = new Runnable() {
            @Override
            public void run() {
            //    Toast.makeText(Home.this, "1", Toast.LENGTH_SHORT).show();
                getData();

                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(runable,5000);
*/

        // getData();
       // setTitle("sfse1");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

      //  NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    @Override
    public void onBackPressed() {        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
            Intent d = new Intent(getApplicationContext(),Login.class);
            startActivity(d);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.content_home, container, false);

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //Returning the current tabs
            switch (position) {
                case 0:
                    HomeTab_1 tab1 = new HomeTab_1();
                    return tab1;
                case 1:
                    HomeTab_2 tab2 = new HomeTab_2();
                    return tab2;
                case 2:
                    HomeTab_3 tab3 = new HomeTab_3();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
    //#######################################################################################3 grt data $$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            HttpPost httppost;
            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());


                    httppost = new HttpPost("http://192.168.0.3/MRTS/patient/"+ "emergencyRecive.php?p_id=" +"11223344");

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
                    Toast.makeText(getApplicationContext(), "dd", Toast.LENGTH_SHORT).show();
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
               //     Toast.makeText(Home.this, "muj "+myJSON, Toast.LENGTH_SHORT).show();
                    showList();

                }catch (Exception f){

                //    Toast.makeText(getApplicationContext(), "error recive ::"+f, Toast.LENGTH_SHORT).show();
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

           // FamilyMemberID = new String[cnt];
            PrimaryID = new String[cnt];
                    MemberID = new String[cnt];
                    Primary_EmergencyAction = new String[cnt];
                    Secondary_EmergencyAction = new String[cnt];
                    OtherSenderID = new String[cnt];
                    Location = new String[cnt];
                    Date = new String[cnt];
                    PrimaryLastName = new String[cnt];
                    PrimaryFirstName = new String[cnt];
                    PrimaryMiddleName = new String[cnt];
                    PrimaryContactNo = new String[cnt];

                    MemberLastName = new String[cnt];
                    MemberFirstName = new String[cnt];
                    MemberMiddleName = new String[cnt];
                    MemberContactNo = new String[cnt];

                    OtherLastName = new String[cnt];
                    OtherFirstName = new String[cnt];
                    OtherMiddleName = new String[cnt];
                    OtherContactNo = new String[cnt];

           // Toast.makeText(this, "p "+peoples.length(), Toast.LENGTH_SHORT).show();
            if(peoples.length()==0){
                xx=1;
            }
            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);



             //   FamilyMemberID[cnt1] = c.getString("FamilyMemberID");
                PrimaryID[cnt1] = c.getString("PrimaryID");
                        MemberID[cnt1] = c.getString("MemberID");
                        Primary_EmergencyAction[cnt1] = c.getString("Primary_EmergencyAction");
                        Secondary_EmergencyAction[cnt1] = c.getString("Secondary_EmergencyAction");
                        OtherSenderID[cnt1] = c.getString("OtherSenderID");
                        Location[cnt1] = c.getString("Location");
                        Date[cnt1] = c.getString("Date");
                        PrimaryLastName[cnt1] = c.getString("PrimaryLastName");
                        PrimaryFirstName[cnt1] = c.getString("PrimaryFirstName");
                        PrimaryMiddleName[cnt1] = c.getString("PrimaryMiddleName");
                        PrimaryContactNo[cnt1] = c.getString("PrimaryContactNo");

                        MemberLastName[cnt1] = c.getString("MemberLastName");
                        MemberFirstName[cnt1] = c.getString("MemberFirstName");
                        MemberMiddleName[cnt1] = c.getString("MemberMiddleName");
                        MemberContactNo[cnt1] = c.getString("MemberContactNo");

                        OtherLastName[cnt1] = c.getString("OtherLastName");
                        OtherFirstName[cnt1] = c.getString("OtherFirstName");
                        OtherMiddleName[cnt1] = c.getString("OtherMiddleName");
                        OtherContactNo[cnt1] = c.getString("OtherContactNo");
                cnt1++;
            }



            //3333333333333333333333333333333333 >>>>>>>>>>>>>>>>>>>>>>>>>>>>    notification

            if(xx==2) {
                Toast.makeText(this, "xx "+xx, Toast.LENGTH_SHORT).show();
            if(Primary_EmergencyAction[0].equals("Emergency")) {
               PatientName_Temp= m1 = PrimaryLastName[0] + " " + PrimaryFirstName[0] + " " + PrimaryMiddleName[0];
                PatientContactNo=PrimaryContactNo[0];
            }
            else if(Secondary_EmergencyAction[0].equals("Emergency")) {
                PatientName_Temp= m1 = MemberLastName[0] + " " + MemberFirstName[0] + " " + MemberMiddleName[0];
                PatientContactNo=MemberContactNo[0];
                }
                // t1=" @ "+chat[xx];

              Location_Temp=  m2 = Location[0];
             Date_Temp=Date[0];
                SenderName_Temp= m1 = OtherLastName[0] + " " + OtherFirstName[0] + " " + OtherMiddleName[0];
                SenderNo_Temp=OtherContactNo[0];

                  /*  m3=mobile[xx];
                    t3=" @ "+chat[xx];

                    m4=mobile[xx];
                    t4=" @ "+chat[xx];

                    m5=mobile[xx];
                    t5=" @ "+chat[xx];*/


                //Intent resultIntent = new Intent(this, Emergency.class);
               // resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
               // PendingIntent piResult = PendingIntent.getActivity(this, 0, resultIntent, 0);

               /* Notification.Builder builder = new Notification.Builder(this)
                        .setSmallIcon(R.drawable.ic_stat_name)
                        .setContentTitle("My Chat App")
                        .setColor(Color.parseColor("#f91010"))
                        .setVibrate(new long[]{100, 250, 100, 250, 100, 550})
                        .setContentText(" messages")
                        .setContentIntent(piResult)
                        .setSound(Uri.parse("android.resource://com.techiessquad.mrtn.patient/" + R.raw.noti))
                        .setAutoCancel(true);


                Notification notification = new Notification.InboxStyle(builder)
                        //    .setContentTitle(mobile[xx])
                        //   .setContentText(chat[xx]+"\n test")

                        .addLine("Patient :" + m1)
                        .addLine("Location :" + m2)*/
                      /*  .addLine(m3+t3)
                        .addLine(m4+t4)
                        .addLine(m5+t5)*/
                     /*   .setBigContentTitle("Emergency !!!")
                        .setSummaryText(Date[0]).build();

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(121, notification);
**/
            }

       //     layout_casepaper a = new layout_casepaper(this(), PatientID, Action, OtherSenderID, Location, OtherLastName, OtherFirstName);
         //   listViewCasePaper.setAdapter(a);


            if(cnt==0) {
                xx=1;
                //State_and_City.emergncyFlg=1;
            //    Toast.makeText(this, "No Products Available", Toast.LENGTH_LONG).show();
            }else {
                xx=2;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //image download start
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {

            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            //Toast.makeText(demo.this, "Error="+res, Toast.LENGTH_SHORT).show();
        }
    }
    //img end
}