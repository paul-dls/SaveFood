package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Vector;

public class ListeCourseActivity extends AppCompatActivity {
    private String nomProduit;
    private int quantite;
    private CourseOperations courseOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_course);
        courseOperations = new CourseOperations(this);
        ListView listeCourse = findViewById(R.id.listeCourse);

        //affichage BDD dans le listView
        try {

            //création de l'objet permettant de modifier la BDD
            courseOperations = new CourseOperations(this);
            // On stocke dans le vecteur "lAlimentss" la liste des aliments
            // contenus dans la table "aliments" de la base de données
            Vector<Produit> lProduits;
            courseOperations.open();
            //alimentsOperations.vider();

            lProduits = courseOperations.listAllProduits();
            courseOperations.close();


            // On associe au modèle de la ListView le vecteur de contacts
            // "lContacts"
            if (lProduits != null) {
                String[] anArrayString = new String[lProduits.size()+1];
                anArrayString[0]="id - nom du Produit - quantite";
                for (int i = 0; i < lProduits.size(); i++) {
                    String s = lProduits.get(i).getId() + " - " +
                            lProduits.get(i).getNomProduit() + " - " +
                            lProduits.get(i).getQuantite();
                    anArrayString[i+1] = s;
                }
                ArrayAdapter<String> listArrayAdapter =
                        new ArrayAdapter<String>(this,
                                android.R.layout.simple_list_item_1,
                                anArrayString);
                listeCourse.setAdapter(listArrayAdapter);
            }
        }catch(Exception e){
            Log.i("erreur BDD antonine", e.toString());
        }

    }


    //ajout d'un produit dans la liste de course
    public void AjouterProduitCourse(View view) {
        EditText editQuantite = findViewById(R.id.textQuantiteProduit);
        EditText editNomProduit = findViewById(R.id.textProductName);
        nomProduit = editNomProduit.getText().toString();
        quantite = Integer.parseInt(editQuantite.getText().toString());
        courseOperations.open();
        courseOperations.addProduit(new Produit(nomProduit,quantite
        ));
        courseOperations.close();


    }
}