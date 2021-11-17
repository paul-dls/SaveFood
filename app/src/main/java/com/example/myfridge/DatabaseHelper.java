package com.example.myfridge;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
    // Version de la base de données
    private static final int VERSION_BASE_DE_DONNEES = 2;
    // Nom du fichier contenant la base de données
    private static final String NOM_BASE_DE_DONNEES = "Aliments.db";
    // Nom de la table qui sera créée dans la base de données
    private static final String TABLE_ALIMENTS = "aliments";
    // Les champs de la table
    private static final String ID = "id"; //codebarre
    private static final String NOM_PRODUIT = "nom_produit";
    private static final String DATE_AJOUT = "date_ajout";
    private static final String DATE_PEREMPTION = "date_peremption";
    private static final String QUANTITE = "quantite";

    // Requête SQL de création de la table "contact" dans la base de données
    private static final String REQUETE_CREATION_TABLE =
            "create table " + TABLE_ALIMENTS +
                    "(" + ID + " integer primary key autoincrement, " +
                    NOM_PRODUIT + " text not null, " +
                    DATE_AJOUT + " text not null," +
                    DATE_PEREMPTION + "text not null," +
                    QUANTITE + "text not null"+") ;";

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
        Log.i("erreur BDD Antonine", "appel onCreate de dataBaseHelper");
    }

    public void onReCreate(SQLiteDatabase db) {
        db.execSQL(REQUETE_SUPPRESSION_TABLE);
        onCreate(db);
        Log.i("erreur BDD Antonine", "appel onCreate de dataBaseHelper");
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

    public  String getDate_Peremption() {
        return DATE_PEREMPTION;
    }

    public String getQuantite() {
        return QUANTITE;
    }
}
