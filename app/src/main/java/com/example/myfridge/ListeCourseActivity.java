package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.database.MatrixCursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.Vector;

public class ListeCourseActivity<itemClickListener> extends AppCompatActivity {
    private String nomProduit;
    private int quantite;
    private CourseOperations courseOperations;
    private ListView listeCourse;
    Vector<Produit> lProduits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_course);
        courseOperations = new CourseOperations(this);
        listeCourse = findViewById(R.id.listeCourse);


        //définitions des colonnes du tableau
        //SimpleCursorAdapter a besoin obligatoirement d'un ID nommé "_id"
        String[] columns = new String[] { "_id", "nom du Produit", "quantite" };
        // Définition des données du tableau
        // les lignes ci-dessous ont pour seul but de simuler
        // un objet de type Cursor pour le passer au SimpleCursorAdapter.
        // Si vos données sont issues d'une base SQLite,
        // utilisez votre "cursor" au lieu du "matrixCursor"
        MatrixCursor matrixCursor= new MatrixCursor(columns);
        matrixCursor.addRow(new Object[] { 1,"nom du produit","quantité" });
        //affichage BDD dans le listView
        try {
            startManagingCursor(matrixCursor);
            //création de l'objet permettant de modifier la BDD
            courseOperations = new CourseOperations(this);
            // On stocke dans le vecteur "lAlimentss" la liste des aliments
            // contenus dans la table "aliments" de la base de données

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
                    matrixCursor.addRow(new Object[] { i+2,lProduits.get(i).getNomProduit(),lProduits.get(i).getQuantite() });
                }
                ArrayAdapter<String> listArrayAdapter =
                        new ArrayAdapter<String>(this,
                                android.R.layout.simple_list_item_1,
                                anArrayString);
                //listeCourse.setAdapter(listArrayAdapter);
            }
        }catch(Exception e){
            Log.i("erreur BDD antonine", e.toString());
        }


// on prendra les données des colonnes 1 et 2...
        String[] from = new String[] {"nom du Produit", "quantite"};

// ...pour les placer dans les TextView définis dans "row_item.xml"
        int[] to = new int[] { R.id.textViewnom, R.id.textViewquantite};

// création de l'objet SimpleCursorAdapter...
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.element_tableau_course, matrixCursor, from, to, 0);

// ...qui va remplir l'objet ListView
        ListView lv = (ListView) findViewById(R.id.listeCourse);
        listeCourse.setAdapter(adapter);

        // Utilisation avec notre listview
        listeCourse.setOnItemClickListener(itemClickListener);

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

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
            int idProduit = lProduits.get(position).getId();
            Toast.makeText(getBaseContext() , String.valueOf(idProduit), Toast.LENGTH_SHORT).show();
            Log.d("mydebug","clic sur id:"+id);
        }
    };


}