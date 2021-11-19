package com.example.myfridge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.Vector;

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

    /*
     * Affichage des contacts contenus dans la table "contacts"
     */
    public Vector<Produit> listAllProduits() {

        String tabColonne[] = new String [3];
        Vector<Produit> lProduits = new Vector<Produit>();

        // La requête renvoie l’id, le nom et le numéro de téléphone des
        // contacts.
        tabColonne[0] = dbHelper.getID();
        tabColonne[1] = dbHelper.getNomProduit();
        tabColonne[2] = dbHelper.getQUANTITE();

        // On exécute la requête. Le résultat est stocké dans la variable
        // cursor.
        Cursor cursor = database.query(dbHelper.getTableProduits(),
                tabColonne,
                null, null,	null, null,	null);


        // On récupère les valeurs des colonnes "id", "nom" et
        // "numero_telephone" des enregistrements stockés dans l'objet
        // cursor. On construit un vecteur de "Contact"
        int numeroColonneId = cursor.getColumnIndexOrThrow(dbHelper.getID());
        int numeroColonneNomProduit =
                cursor.getColumnIndexOrThrow(dbHelper.getNomProduit());
        int numeroColonneQuantite =
                cursor.getColumnIndexOrThrow(dbHelper.getQUANTITE());
        if (cursor.moveToFirst() == true) {
            do {
                int id = cursor.getInt(numeroColonneId);
                String nomProduit = cursor.getString(numeroColonneNomProduit);
                int quantite = cursor.getInt(numeroColonneQuantite);
                Produit p = new Produit(nomProduit, quantite);
                lProduits.add(p);
            } while (cursor.moveToNext());
        }

        return lProduits;
    }


}

