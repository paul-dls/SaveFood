package com.example.myfridge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Vector;

public class AlimentsOperations {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    //le constructeur
    public AlimentsOperations(Context context){dbHelper = new DatabaseHelper(context);}

    // réinitialiser BDD
    public void vider(){
        //database.execSQL("DELETE FROM aliments");
        dbHelper.onReCreate(database);
        Log.i("erreur BDD Antonine ", database.toString());
    }

    // creation de la base de données avec accès lecture et écriture
    public void open(){
        database = dbHelper.getWritableDatabase();
    }

    //Fermeture de la connexion à la base de donnée
    public void close() {
        dbHelper.close();
    }
    public long addAliments(Aliments a) {

        ContentValues valeurs = new ContentValues();

        // Initialisation de la variable valeurs avec les couples de
        // valeurs suivants :
        //    - (NOM, nom)
        //    - (NUMERO_TELEPHONE, numeroTelephone)
        valeurs.put(dbHelper.getId(),a.getId());
        valeurs.put(dbHelper.getNom_Produit(), a.getNom_produit());
        valeurs.put(dbHelper.getDate_Ajout(), a.getDate_ajout());
        valeurs.put(dbHelper.getDate_Peremption(),a.getDate_peremption());
        valeurs.put(dbHelper.getQuantite(),a.getQuantite());

        // Insertion de l'enregistrement dans la table "contacts"
        long AlimentId = database.insert(dbHelper.getTableAliments(),
                null,
                valeurs);

        return AlimentId;
    }

    //Affichage des aliments contenus dans la table "aliments"
    public Vector<Aliments> listAllAliments(){

        String tabColonne[] = new String [5];
        Vector<Aliments> lAliments = new Vector<Aliments>();

        // La requête renvoie l’id, le nom et le numéro de téléphone des
        // contacts.
        tabColonne[0] = dbHelper.getId();
        tabColonne[1] = dbHelper.getNom_Produit();
        tabColonne[2] = dbHelper.getDate_Ajout();
        tabColonne[3] = dbHelper.getDate_Peremption();
        tabColonne[4] = dbHelper.getQuantite();

        // On exécute la requête. Le résultat est stocké dans la variable
        // cursor.
        Cursor cursor = database.query(dbHelper.getTableAliments(),
                tabColonne,
                null, null,	null, null,	null);


        // On récupère les valeurs des colonnes "id", "nom_produit" et
        // "date_ajout" des enregistrements stockés dans l'objet
        // cursor. On construit un vecteur de "Aliments"
        int numeroColonneId = cursor.getColumnIndexOrThrow(dbHelper.getId());
        int numeroColonneNom_Produit =
                cursor.getColumnIndexOrThrow(dbHelper.getNom_Produit());
        int numeroColonneDate_Ajout =
                cursor.getColumnIndexOrThrow(dbHelper.getDate_Ajout());
        int numeroColonneDate_Peremption = cursor.getColumnIndexOrThrow(dbHelper.getDate_Peremption());
        int numeroColonneQuantite = cursor.getColumnIndexOrThrow(dbHelper.getQuantite());
        if (cursor.moveToFirst() == true) {
            do {
                String id = cursor.getString(numeroColonneId);
                String nom_produit = cursor.getString(numeroColonneNom_Produit);
                String date_ajout =
                        cursor.getString(numeroColonneDate_Ajout);
                String date_peremption = cursor.getString(numeroColonneDate_Peremption);
                int quantite = cursor.getInt(numeroColonneQuantite);
                Aliments c = new Aliments(id,nom_produit,date_ajout,date_peremption,quantite);
                lAliments.add(c);
            } while (cursor.moveToNext());
        }

        return lAliments;
    }

    public Vector<String> listeNomAliment() {
        Vector<String> listeNomAliment = new Vector<String>();
        Cursor cursor = database.rawQuery("SELECT nom_produit FROM aliments", new String[]{});
        int numeroColonneNom_Produit = cursor.getColumnIndexOrThrow(dbHelper.getNom_Produit());
        if (cursor.moveToFirst() == true) {
            do {
                String nom_produit = cursor.getString(numeroColonneNom_Produit);
                listeNomAliment.add(nom_produit);
            } while (cursor.moveToNext());
        }
        return listeNomAliment;
    }
}

