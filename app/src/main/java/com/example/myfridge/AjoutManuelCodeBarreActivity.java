package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

public class AjoutManuelCodeBarreActivity extends AppCompatActivity {
    String codebarre;
    String nomProduit;
    private Aliments aliment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_manuel);

        Intent deScanActivity = getIntent();
        aliment = (Aliments)deScanActivity.getSerializableExtra("aliment");

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
                aliment.setNom_produit(nomProduit);
                aliment.setId(codebarre);
                Intent versAjoutAliment = new Intent();
                versAjoutAliment.setClass(getBaseContext(), AjoutAliment.class);
                versAjoutAliment.putExtra("aliment",aliment);
                startActivity(versAjoutAliment);
                Toast.makeText(getBaseContext(),nomProduit, Toast.LENGTH_SHORT).show();
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



    public void EntreeCodebarre(View view) {
        EditText editCodebarre = findViewById(R.id.editCodebarre);
        codebarre = editCodebarre.getText().toString();

        //requête internet pour trouver le nom du produit
        String url= "https://world.openfoodfacts.org/api/v0/product/" + codebarre + ".json";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Response.Listener<String> responseListener = new AjoutManuelCodeBarreActivity.urlResponseListener();
        Response.ErrorListener responseErrorListener = new AjoutManuelCodeBarreActivity.urlResponseErrorListener();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener,responseErrorListener);
        requestQueue.add(stringRequest);


    }
}