package com.example.myfridge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

//classe pour afficher un unique élément sélectionné dans le Spinner

public class CustomAdapter extends BaseAdapter {
    Context context;
    String[] NomAliments;
    //int[] QuantiteAliments;
    LayoutInflater inflater;
    String[] urlImageAliments;

    public CustomAdapter(AffichageFrigoActivity affichageFrigoActivity, String[] nomsAliments, String [] urlImageAliments) {
        this.context = affichageFrigoActivity;
        this.NomAliments=nomsAliments;
        //this.QuantiteAliments=quantiteAliments;
        this.urlImageAliments=urlImageAliments;
        inflater =(LayoutInflater.from(affichageFrigoActivity));
    }

    @Override
    public int getCount() {
        return NomAliments.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.affichage_aliment_spinner,null);
        //TextView texteQuantite = (TextView) convertView.findViewById(R.id.textViewNombre);
        TextView texteNomProduit = (TextView) convertView.findViewById(R.id.textViewNomProduit);
        //texteQuantite.setText(String.valueOf(QuantiteAliments[position]));
        texteNomProduit.setText(NomAliments[position]);
        ImageView icon = (ImageView) convertView.findViewById(R.id.image_view);
        new DownloadImageFromInternet(icon).execute(urlImageAliments[position]);
        return convertView;
    }

    private class DownloadImageFromInternet extends AsyncTask<String,Void, Bitmap> {
        ImageView imageView;
        public DownloadImageFromInternet(ImageView icon) {
            this.imageView=icon;
            //Toast.makeText(context.getApplicationContext(), "Please wait, it may take a few minutes to load ...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String imageUrl=urls[0];
            Bitmap bimage = null;
            try{
                InputStream in= new java.net.URL(imageUrl).openStream();
                bimage= BitmapFactory.decodeStream(in);
            }catch(Exception e){
                Log.i("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

}
