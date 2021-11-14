package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class getInfoActivity extends AppCompatActivity {
    String codebarre;
    String nomProduit;
    String dateAjout;
    String datePeremption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info);

        //recupération du code barre
        Intent deMainActivity = getIntent();
        codebarre = deMainActivity.getStringExtra("codebarre");

        //requête internet pour trouver la nom du produit
        String url= "https://world.openfoodfacts.org/api/v0/product/" + codebarre + ".json";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Response.Listener<String> responseListener = new urlResponseListener();
        Response.ErrorListener responseErrorListener = new urlResponseErrorListener();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener,responseErrorListener);
        requestQueue.add(stringRequest);

        // récupération date d'ajout
        Calendar calendrier=Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM -yyyy");
        dateAjout= df.format(calendrier.getTime());

    }

    public void ajouterDate(View view) {
        DatePicker simpleDatePicker = (DatePicker) findViewById(R.id.datePicker1); // initiate a date picker
        String day = String.valueOf(simpleDatePicker.getDayOfMonth()); // get the selected day of the month
        String month =String.valueOf(simpleDatePicker.getMonth()); // get the selected month
        String year = String.valueOf(simpleDatePicker.getYear()); // get the selected year

        datePeremption= day + "-" +month + "-"+year;

        //affichage du nom du produit
        TextView textView = findViewById(R.id.nomProduit);
        textView.setText("nom du Produit: " + nomProduit +"\n" + "Date de l'ajout: " + dateAjout + "\n" + "date de péremption: " + datePeremption);


    }

    private class urlResponseListener implements Response.Listener<String> {
        @Override
        public void onResponse(String response) {
            try {
                //récupération nom du produit à partir du résultat de la requête internet
                JSONObject obj = new JSONObject(response);
                Log.i("test", "recupère JSON");
                JSONObject product = obj.getJSONObject("product");
                Log.i("test", "recupère JSON product");
                nomProduit = product.getString("product_name");
                Log.i("test", "nomProduit");


                Log.i("test", "affiche nom Produit");
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i("test", e.toString());
            }

        }
    }
    private class urlResponseErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }
}
