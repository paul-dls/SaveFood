package com.example.myfridge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

//classe pour afficher un unique élément sélectionné dans le Spinner

public class CustomAdapter extends BaseAdapter {
    Context context;
    String[] NomAliments;
    String[] QuantiteAliments;
    LayoutInflater inflater;

    public CustomAdapter(AffichageFrigoActivity affichageFrigoActivity, String[] nomsAliments, String[] quantiteAliments) {
        this.context = affichageFrigoActivity;
        this.NomAliments=nomsAliments;
        this.QuantiteAliments=quantiteAliments;
        inflater =(LayoutInflater.from(affichageFrigoActivity));
    }

    @Override
    public int getCount() {
        return NomAliments.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.affichage_aliment_spinner,null);
        TextView texteQuantite = (TextView) convertView.findViewById(R.id.textViewNombre);
        TextView texteNomProduit = (TextView) convertView.findViewById(R.id.textViewNomProduit);
        texteQuantite.setText(QuantiteAliments[position]);
        texteNomProduit.setText(NomAliments[position]);
        return convertView;
    }
}
