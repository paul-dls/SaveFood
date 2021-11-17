package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

public class AjoutManuelDateActivity extends AppCompatActivity {
    //String codebarre;
    //String nomProduit;
    //String dateAjout;
    String datePeremption;
    private Aliments aliment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_manuel_date_activity);

        Intent deScanActivity = getIntent();
        aliment = (Aliments)deScanActivity.getSerializableExtra("aliment");

        //recupération du code barre
        //Intent deMainActivity = getIntent();
        //codebarre = deMainActivity.getStringExtra("codebarre");

        /*
        //requête internet pour trouver le nom du produit
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
        Log.i("fonctionnement normal", "date d'ajout récupérée");

        */
    }
    //récupération de la date de péremption entrée par l'utilisateur et de la date d'ajout du produit, pour la transmettre à la BDD
    public void ajouterDatePeremption(View view) {

        //récupération de la date de péremption
        DatePicker simpleDatePicker = (DatePicker) findViewById(R.id.datePicker1); // initiate a date picker
        String day = String.valueOf(simpleDatePicker.getDayOfMonth()); // get the selected day of the month
        String month =String.valueOf(simpleDatePicker.getMonth()); // get the selected month
        String year = String.valueOf(simpleDatePicker.getYear()); // get the selected year

        datePeremption= day + "-" +month + "-"+year;
        Log.i("fonctionnement normal", "date de péremption récupérée");

        aliment.setDate_peremption(datePeremption);
        Intent versAjoutAliment = new Intent();
        versAjoutAliment.setClass(this, AjoutAliment.class);
        versAjoutAliment.putExtra("aliment",aliment);
        startActivity(versAjoutAliment);

        /*
        //affichage données du produit
        TextView textView = findViewById(R.id.nomProduit);
        textView.setText("codebarre: " + codebarre + "nom du Produit: " + nomProduit + "\n" + "Date de l'ajout: " + dateAjout + "\n" + "date de péremption: " + datePeremption);


        //Ajout de l'aliment dans la base de données
        AlimentsOperations alimentsOperations = new AlimentsOperations(this);
        alimentsOperations.open();
        Log.i("getInfo","ouverture BDD");
        alimentsOperations.addAliments(new Aliments(codebarre,nomProduit,dateAjout));
        Log.i("getInfo","ajout Aliment");
        alimentsOperations.close();
        Log.i("getInfo","fermeture BDD");

         */

        /*
        //retour Home
        Intent versHome= new Intent();
        versHome.setClass(this,NavigationDrawerActivity.class);
        startActivity(versHome);
        */

    }




    /*
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

     */
}
