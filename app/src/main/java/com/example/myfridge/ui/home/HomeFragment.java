package com.example.myfridge.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myfridge.AjoutAliment;
import com.example.myfridge.Aliments;
import com.example.myfridge.AlimentsOperations;
import com.example.myfridge.R;
import com.example.myfridge.databinding.FragmentHomeBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private Context context= getActivity();

    private AlimentsOperations alimentsOperations;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });



        Log.i("bug BDD","entre dans le onCreate de main");

        try {
            //affichage de la liste des aliments de la BDD dans le listView
            ListView listViewAliment = root.findViewById(R.id.listViewAliments);
            //création de l'objet permettant de modifier la BDD
            alimentsOperations = new AlimentsOperations(context);
            // On stocke dans le vecteur "lAlimentss" la liste des aliments
            // contenus dans la table "aliments" de la base de données
            Vector<Aliments> lAliments;
            alimentsOperations.open();
            //alimentsOperations.vider();

            lAliments = alimentsOperations.listAllAliments();
            alimentsOperations.close();

            // On associe au modèle de la ListView le vecteur de contacts
            // "lContacts"
            if (lAliments != null) {
                String[] anArrayString = new String[lAliments.size()];
                for (int i = 0; i < lAliments.size(); i++) {
                    String s = lAliments.get(i).getId() + " - " +
                            lAliments.get(i).getNom_produit() + " : " +
                            lAliments.get(i).getDate_ajout()+ ":" +
                            lAliments.get(i).getDate_peremption()+ ":"+
                            lAliments.get(i).getQuantite();
                    anArrayString[i] = s;
                }
                ArrayAdapter<String> listArrayAdapter =
                        new ArrayAdapter<String>(context,
                                android.R.layout.simple_list_item_1,
                                anArrayString);
                listViewAliment.setAdapter(listArrayAdapter);
            }
        }catch(Exception e){
            Log.i("erreur BDD antonine", e.toString());
        }


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void ajouter(View view) {
        // récupération date d'ajout
        Calendar calendrier=Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM -yyyy");
        String dateAjout= df.format(calendrier.getTime());
        Log.i("fonctionnement normal", "date d'ajout récupérée");
        Log.i("scan", "rentre dans le onClick");
        Intent VersAjoutAliment= new Intent();
        VersAjoutAliment.setClass(context, AjoutAliment.class);
        VersAjoutAliment.putExtra("aliment", new Aliments("1","non communiqué",dateAjout,"non communiqué",1));

        startActivity(VersAjoutAliment);
        Log.i("scan", "intent vers AjoutAliment");
    }

}