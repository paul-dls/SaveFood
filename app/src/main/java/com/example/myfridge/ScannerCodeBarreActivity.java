package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class ScannerCodeBarreActivity extends AppCompatActivity {
    String codebarre;
    String nomProduit;
    Aliments aliment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("erreur intent paul","oncreate du scan appelé");
        Intent deScanActivity = getIntent();
        aliment = (Aliments)deScanActivity.getSerializableExtra("aliment");
        Log.i("erreur intent paul","aliment.getId()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_code_barre);

        //Initiation du Scanner
        IntentIntegrator intentIntegrator = new IntentIntegrator(ScannerCodeBarreActivity.this);
        intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setPrompt("SCAN"); //Set a prompt to display on the capture screen, instead of using the default.
        intentIntegrator.setBarcodeImageEnabled(false); //set to true enables saving the barcode image and sending its paths in the result intent
        intentIntegrator.initiateScan();

        //Intent deMainActivity = getIntent();

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
                aliment.setId(codebarre);
                aliment.setNom_produit(nomProduit);

                Intent versAjoutAlimentActivity = new Intent();
                versAjoutAlimentActivity.setClass(getBaseContext(), AjoutAliment.class);
                versAjoutAlimentActivity.putExtra("aliment",aliment);
                startActivity(versAjoutAlimentActivity);
                finish();

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

                //requête internet pour trouver le nom du produit
                String url= "https://world.openfoodfacts.org/api/v0/product/" + codebarre + ".json";
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                Response.Listener<String> responseListener = new ScannerCodeBarreActivity.urlResponseListener();
                Response.ErrorListener responseErrorListener = new ScannerCodeBarreActivity.urlResponseErrorListener();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener,responseErrorListener);
                requestQueue.add(stringRequest);

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