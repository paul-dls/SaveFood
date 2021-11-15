package com.example.myfridge;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    // Version de la base de données
    private static final int VERSION_BASE_DE_DONNEES = 1;
    // Nom du fichier contenant la base de données
    private static final String NOM_BASE_DE_DONNEES = "aliments.db";
    // Nom de la table qui sera créée dans la base de données
    private static final String TABLE_ALIMENTS = "aliments";
    // Les champs de la table
    private static final String ID = "id"; //codebarre
    private static final String NOM_PRODUIT = "nom_produit";
    private static final String DATE_AJOUT = "date_ajout";

    // Requête SQL de création de la table "contact" dans la base de données
    private static final String REQUETE_CREATION_TABLE =
            "create table " + TABLE_ALIMENTS +
                    "(" + ID + " integer primary key autoincrement, " +
                    NOM_PRODUIT + " text not null, " +
                    DATE_AJOUT + " text not null) ;";

    // Requête SQL de suppression de la table "contacts" dans la base de données
    private static final String REQUETE_SUPPRESSION_TABLE =
            "DROP TABLE IF EXISTS " +
                    TABLE_ALIMENTS + ";";

    /*
     * Le constructeur
     */
    public DatabaseHelper(Context context) {
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

    public String getNom_Produit() {
        return NOM_PRODUIT;
    }

    public String getDate_Ajout() {
        return DATE_AJOUT;
    }

    public String getTableAliments() {
        return TABLE_ALIMENTS;
    }

    public String getId() {
        return ID;
    }
}
