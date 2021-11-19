package com.example.myfridge;

public class Produit {
    private int id;
    private String nomProduit;
    private int quantite;

    public Produit(String nomProduit, int quantite) {
        this.nomProduit = nomProduit;
        this.quantite = quantite;
        }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public int getId() {
        return id;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public int getQuantite() {
        return quantite;
    }
}
