package com.example.myfridge;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CourseDatabaseHelper extends SQLiteOpenHelper{
    // Version de la base de données
    private static final int VERSION_BASE_DE_DONNEES = 1;
    // Nom du fichier contenant la base de données
    private static final String NOM_BASE_DE_DONNEES = "Listedecourse.db";
    // Nom de la table qui sera créée dans la base de données
    private static final String TABLE_PRODUITS = "produits";
    // Les champs de la table
    private static final String ID = "id";
    private static final String NOM_PRODUIT = "nom";
    private static final String QUANTITE = "quantite";

    // Requête SQL de création de la table "contact" dans la base de données
    private static final String REQUETE_CREATION_TABLE =
            "create table " + TABLE_PRODUITS +
                    "(" + ID + " integer primary key autoincrement, " +
                    NOM_PRODUIT + " text not null, " +
                    QUANTITE + " text not null) ;";

    // Requête SQL de suppression de la table "contacts" dans la base de données
    private static final String REQUETE_SUPPRESSION_TABLE =
            "DROP TABLE IF EXISTS " +
                    TABLE_PRODUITS + ";";

    /*
     * Le constructeur
     */
    public CourseDatabaseHelper(Context context) {
        super(context, NOM_BASE_DE_DONNEES, null, VERSION_BASE_DE_DONNEES);
    }

    /*
     * Création de la table "contacts" dans la base de données
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(REQUETE_CREATION_TABLE);
    }

    /*
     * Mise à jour de la base de données
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(REQUETE_SUPPRESSION_TABLE);
        onCreate(db);
    }

    public static String getQUANTITE() {
        return QUANTITE;
    }

    public static String getNomProduit() {
        return NOM_PRODUIT;
    }

    public static String getTableProduits() {
        return TABLE_PRODUITS;
    }

    public static String getID() {
        return ID;
    }
}
