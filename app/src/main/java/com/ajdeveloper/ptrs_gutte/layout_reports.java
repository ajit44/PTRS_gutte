package com.ajdeveloper.ptrs_gutte;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

/**
 * Created by ajits on 17-12-2017.
 */

public class layout_reports extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] doc_name;
    private final String[] d_date;

    private final String[] posters;



   public layout_reports(Activity context,  String[]doc_name, String[]d_date, String[] posters){
        super(context, R.layout.layout_reports,doc_name);

        this.context=context;
       this.doc_name=doc_name;
       this.d_date=d_date;

       this.posters=posters;




   }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.layout_reports, null, true);

        TextView description_TYPE=(TextView)rowView.findViewById(R.id.name);
        description_TYPE.setText(doc_name[position]);

        TextView description_date=(TextView)rowView.findViewById(R.id.date);
        description_date.setText(d_date[position]);





        new DownloadImageTask((ImageView) rowView.findViewById(R.id.img))
                .execute(posters[position].replace(" ", "%20"));






        return rowView;
    }
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

//loop and download image and put it in this list
            //  imagesList.add(result);
            bmImage.setImageBitmap(result);
            //Toast.makeText(demo.this, "Error="+res, Toast.LENGTH_SHORT).show();
        }
    }
}
