package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AffichageUnAliment extends AppCompatActivity {
    String codebarre;
    String dateAjout;
    String datePeremption;
    int quantite;
    String nomProduit;
    private AlimentsOperations alimentsOperations;
    TextView textquantite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_un_aliment);

        Intent deAffichageFrigo = getIntent();
        Aliments aliments = (Aliments) deAffichageFrigo.getSerializableExtra("alimentSelectionne");
        codebarre=aliments.getId();
        datePeremption= aliments.getDate_peremption();
        dateAjout= aliments.getDate_ajout();
        nomProduit= aliments.getNom_produit();
        quantite= aliments.getQuantite();


        TextView infoProduit = findViewById(R.id.textProduit);
        infoProduit.setText("codebarre: " + codebarre +"\n"+
                            "nom du Produit: " + nomProduit +"\n"+
                            "date d'ajout: "+ dateAjout +"\n"+
                            "date de péremption: "+datePeremption);

        textquantite = findViewById(R.id.textQuantite);
        textquantite.setText( "quantité: "+ "\n" + String.valueOf(quantite));

    }
    //augmenter la quantité d'aliments déjà dans le frigo
    public void PlusQuantite(View view) {
        AlimentsOperations alimentsOperations = new AlimentsOperations(this);
        alimentsOperations.open();
        alimentsOperations.ModifierQuantite(codebarre,quantite+1,  dateAjout, datePeremption, nomProduit);
        quantite= alimentsOperations.RetourneQuantite(codebarre,quantite);
        textquantite.setText( "quantité: "+ "\n" + String.valueOf(quantite));
        alimentsOperations.close();
    }
    //retirer des aliments
    public void MoinsQuantite(View view) {
        if(quantite>1) {
            AlimentsOperations alimentsOperations = new AlimentsOperations(this);
            alimentsOperations.open();
            alimentsOperations.ModifierQuantite(codebarre, quantite - 1,  dateAjout, datePeremption, nomProduit);
            quantite = alimentsOperations.RetourneQuantite(codebarre, quantite);
            textquantite.setText( "quantité: "+ "\n" + String.valueOf(quantite));
            alimentsOperations.close();
        }else{
            AlimentsOperations alimentsOperations = new AlimentsOperations(this);
            alimentsOperations.open();
            alimentsOperations.EffacerAliment(codebarre);
            alimentsOperations.close();
            Intent versMainActivity = new Intent();
            versMainActivity.setClass(this,MainActivity.class);
            startActivity(versMainActivity);
        }
    }

    public void effacerAliment(View view) {
        AlimentsOperations alimentsOperations = new AlimentsOperations(this);
        alimentsOperations.open();
        alimentsOperations.EffacerAliment(codebarre);
        alimentsOperations.close();
        Intent versMainActivity = new Intent();
        versMainActivity.setClass(this,MainActivity.class);
        startActivity(versMainActivity);
    }
}