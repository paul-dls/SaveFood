package com.example.myfridge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CourseOperations {
    private CourseDatabaseHelper dbHelper;
    private SQLiteDatabase database;


    /*
     * Le constructeur
     */
    public CourseOperations(Context context) {
        dbHelper = new CourseDatabaseHelper(context);
    }

    /*
     * Création de la base de données avec un accès en lecture et écriture
     */
    public void open(){
        database = dbHelper.getWritableDatabase();
    }

    /*
     * Fermeture de la connexion à la base de données
     */
    public void close() {
        dbHelper.close();
    }
    public long addProduit(Produit p) {

        ContentValues valeurs = new ContentValues();

        // Initialisation de la variable valeurs avec les couples de
        // valeurs suivants :
        //    - (NOM, nom)
        //    - (NUMERO_TELEPHONE, numeroTelephone)
        valeurs.put(dbHelper.getNomProduit(), p.getNomProduit());
        valeurs.put(dbHelper.getQUANTITE(), p.getQuantite());

        // Insertion de l'enregistrement dans la table "contacts"
        long produitId = database.insert(dbHelper.getTableProduits(),
                null,
                valeurs);

        return produitId;
    }

}

