package com.example.myfridge;

import android.util.Log;

import java.io.Serializable;

//création entité aliments
public class Aliments implements Serializable {
    private String id;
    private String nom_produit;
    private String date_ajout;
    private int quantite;
    private String date_peremption;

    public Aliments(String id, String nom_produit, String date_ajout,String date_peremption,int quantite) {
        this.nom_produit = nom_produit;
        this.date_ajout = date_ajout;
        this.id=id;
        this.date_peremption=date_peremption;
        this.quantite=quantite;
    }
    /*
    public Aliments() {
        this.nom_produit = "non communique";
        this.date_ajout = "non communique";
        this.id="non communique";
        this.date_peremption="non communique";
        this.quantite=0;

    }

     */

    //création setter et getter
    public void setId(String id) {
        this.id = id;
    }
    public void setNom_produit(String nom_produit){
        this.nom_produit=nom_produit;
    }
    public void setDate_ajout(String date_ajout) {
        this.date_ajout = date_ajout;
    }

    public void setDate_peremption(String date_peremption) {
        this.date_peremption = date_peremption;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getId() {
        return id;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public String getDate_ajout() {
        return date_ajout;
    }

    public String getDate_peremption() {
        return date_peremption;
    }

    public int getQuantite() {
        return quantite;
    }

    public void description(){
        Log.i("erreur paul aliment", getId()+"/"+getDate_ajout()+"/"+getDate_peremption()+"/"+getNom_produit());

    }
}
