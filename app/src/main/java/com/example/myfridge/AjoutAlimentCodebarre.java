package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AjoutAlimentCodebarre extends AppCompatActivity {
    String codebarre;
    String datePeremption;
    String nomProduit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_aliment_codebarre);

        //récuparation codebarre par scan
        Intent deScanActivity = getIntent();
        codebarre=deScanActivity.getStringExtra("codebarre");
        // nomProduit=deScanActivity.getStringExtra("NomProduit");

        //récupération codebarre manuelle
        Intent deAjoutManuelActivity = getIntent();
        codebarre=deAjoutManuelActivity.getStringExtra("codebarre");
        // nomProduit=deAjoutManuelActivity.getStringExtra("NomProduit");

        //requête internet pour trouver le nom du produit
        String url= "https://world.openfoodfacts.org/api/v0/product/" + codebarre + ".json";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Response.Listener<String> responseListener = new AjoutAlimentCodebarre.urlResponseListener();
        Response.ErrorListener responseErrorListener = new AjoutAlimentCodebarre.urlResponseErrorListener();
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
                Toast.makeText(getBaseContext(),nomProduit, Toast.LENGTH_SHORT).show();
                //affichage des données pour validation par utilisateur ou modification
                TextView editNomProduit = findViewById(R.id.TextNomProduit);
                editNomProduit.setText(nomProduit);


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

    public void AjouterProduit(View view) {
        //transfert vers l'activité pour récupérer les dates
        Intent versAjoutAlimentDate = new Intent();
        versAjoutAlimentDate.setClass(this, AjoutAlimentDate.class);
        versAjoutAlimentDate.putExtra("codebarre",codebarre);
        versAjoutAlimentDate.putExtra("NomProduit", nomProduit);
        startActivity(versAjoutAlimentDate);
    }

    public void EntreeCodeManuel(View view) {
        Intent versAjoutManuel = new Intent();
        versAjoutManuel.setClass(this, AjoutManuelActivity.class);
        startActivity(versAjoutManuel);
    }

    public void ScanCodebarre(View view) {
        Intent versScanActvity = new Intent();
        versScanActvity.setClass(this, ScanActivity.class);
        startActivity(versScanActvity);

    }
}