package com.example.myfridge;

//création entité aliments
public class Aliments {
    private String id;
    private String nom_produit;
    private String date_ajout;

    public Aliments(String id, String nom_produit, String date_ajout) {
        this.nom_produit = nom_produit;
        this.date_ajout = date_ajout;
        this.id=id;
    }

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

    public String getId() {
        return id;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public String getDate_ajout() {
        return date_ajout;
    }
}
