package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

import java.util.Vector;

public class AffichageFrigoActivity extends AppCompatActivity implements AdapterView .OnItemSelectedListener{
    private AlimentsOperations alimentsOperations;
    String[] NomsAliments;
    int[] QuantiteAliments;
    String[] CodebarreAliments;
    String[] DateAjoutAliments;
    String[] DatePeremptionAliments;
    String[] AdresseImageAlimenst;
    Vector<Aliments> lAliments;
    String[] urlImageAliments;
    int j=0;
    int k =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_frigo);

        /*
        // récupération du nom et de la quantite des aliments à afficher dans le spinenr (menu déroulant)
        alimentsOperations = new AlimentsOperations(this);
        Vector<String[]> listeNomQuantiteCode;
        alimentsOperations.open();
        listeNomQuantiteCode = alimentsOperations.listeNomQuantiteCodebarre();
        alimentsOperations.close();
        if (listeNomQuantiteCode != null) {
            NomsAliments =new  String[listeNomQuantiteCode.size()];
            QuantiteAliments= new String [listeNomQuantiteCode.size()];
            CodebarreAliments =new String[listeNomQuantiteCode.size()];
            for (int i = 0; i < listeNomQuantiteCode.size(); i++) {
                String nom = listeNomQuantiteCode.get(i)[0];
                String quantite = listeNomQuantiteCode.get(i)[1];
                String codebarre = listeNomQuantiteCode.get(i)[2];
                NomsAliments[i] = nom;
                QuantiteAliments[i] = quantite;
                CodebarreAliments[i]= codebarre;
            }
        }

        */
        alimentsOperations = new AlimentsOperations(this);
        // On stocke dans le vecteur "lAlimentss" la liste des aliments
        // contenus dans la table "aliments" de la base de données
        alimentsOperations.open();
        //alimentsOperations.vider();

        lAliments = alimentsOperations.listAllAliments();
        alimentsOperations.close();

        // On associe au modèle de la ListView le vecteur de contacts
        // "lContacts"
        if (lAliments != null) {
            NomsAliments =new  String[lAliments.size()];
            QuantiteAliments= new int [lAliments.size()];
            CodebarreAliments =new String[lAliments.size()];
            DateAjoutAliments =new String[lAliments.size()];
            DatePeremptionAliments =new String[lAliments.size()];
            urlImageAliments = new String[lAliments.size()];
            for (int i = 0; i < lAliments.size(); i++) {
                String nom = lAliments.get(i).getNom_produit();
                int quantite = lAliments.get(i).getQuantite();
                String codebarre = lAliments.get(i).getId();
                String dateAjout = lAliments.get(i).getDate_ajout();
                String datePeremption = lAliments.get(i).getDate_peremption();
                NomsAliments[i] = nom;
                QuantiteAliments[i] = quantite;
                CodebarreAliments[i] = codebarre;
                DateAjoutAliments[i] = dateAjout;
                DatePeremptionAliments[i] = datePeremption;

                String url= "https://world.openfoodfacts.org/api/v0/product/" + codebarre + ".json";
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                Response.Listener<String> responseListener = new AffichageFrigoActivity.urlResponseListener();
                Response.ErrorListener responseErrorListener = new AffichageFrigoActivity.urlResponseErrorListener();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener,responseErrorListener);
                requestQueue.add(stringRequest);
            }
        }


        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.simpleSpinner);
        spin.setOnItemSelectedListener(this);

//Creating the ArrayAdapter instance having the bank name list
        CustomAdapter customAdapter = new CustomAdapter(this,NomsAliments,urlImageAliments);
        //ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,NomsAliments);
        //aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(customAdapter);
    }

    private class urlResponseListener implements Response.Listener<String> {
        @Override
        public void onResponse(String response) {
            try {
                //récupération url image du produit à partir du résultat de la requête internet
                JSONObject obj = new JSONObject(response);
                Log.i("test", "recupère JSON");
                JSONObject product = obj.getJSONObject("product");
                Log.i("test", "recupère JSON product");
                String urlImage = product.getString("image_front_small_url");
                Log.i("test", "url récupéré");
                urlImageAliments[j]=urlImage;
                j++;
                //Toast.makeText(getBaseContext(),urlImage, Toast.LENGTH_SHORT).show();

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

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        if (k>0){
        Intent versAffichageUnAliment = new Intent();
        versAffichageUnAliment.setClass(this, AffichageUnAliment.class);
        versAffichageUnAliment.putExtra("alimentSelectionne", lAliments.get(position));
        versAffichageUnAliment.putExtra("urlImage", urlImageAliments[position]);
        startActivity(versAffichageUnAliment);}
        k++;
        Toast.makeText(getApplicationContext(), NomsAliments[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

        // TOD Auto-generated method stub

    }

}