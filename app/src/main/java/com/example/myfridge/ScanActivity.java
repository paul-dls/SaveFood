package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.myfridge.ui.home.AjoutAlimentCodebarre;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class ScanActivity extends AppCompatActivity {
    String codebarre;
    String nomProduit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        //Initiation du Scanner
        IntentIntegrator intentIntegrator = new IntentIntegrator(ScanActivity.this);
        intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setPrompt("SCAN"); //Set a prompt to display on the capture screen, instead of using the default.
        intentIntegrator.setBarcodeImageEnabled(false); //set to true enables saving the barcode image and sending its paths in the result intent
        intentIntegrator.initiateScan();

        //Intent deMainActivity = getIntent();

        //requête internet pour trouver le nom du produit
        String url= "https://world.openfoodfacts.org/api/v0/product/" + codebarre + ".json";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Response.Listener<String> responseListener = new ScanActivity.urlResponseListener();
        Response.ErrorListener responseErrorListener = new ScanActivity.urlResponseErrorListener();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener,responseErrorListener);
        requestQueue.add(stringRequest);
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

            Intent VersAjoutAliment = new Intent();
            VersAjoutAliment.setClass(getBaseContext(), AjoutAlimentCodebarre.class);
            VersAjoutAliment.putExtra("codebarre",codebarre);
            VersAjoutAliment.putExtra("nomProduit",nomProduit);
            startActivity(VersAjoutAliment);
        }
    }
    private class urlResponseErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() == null) {
                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("MainActivity", "Scanned");

                //récupération code barre et appel de l'activité d'affichage du nom du produit
                codebarre = Result.getContents();
                Toast.makeText(this, codebarre, Toast.LENGTH_SHORT).show();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



    /*
    // methode appelée lorsqu'on clique sur go SCAN pour initier le scanner
    public void onClickScanner(View view) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(ScanActivity.this);
        intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setPrompt("SCAN"); //Set a prompt to display on the capture screen, instead of using the default.
        intentIntegrator.setBarcodeImageEnabled(false); //set to true enables saving the barcode image and sending its paths in the result intent
        intentIntegrator.initiateScan();
    }


    public void ajoutManuel(View view) {
        Intent versAjoutManuelActivity= new Intent();
        versAjoutManuelActivity.setClass(this,AjoutManuelActivity.class);
        startActivity(versAjoutManuelActivity);
    }

     */
}