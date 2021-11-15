package com.example.myfridge.ui.home;

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

import com.example.myfridge.Aliments;
import com.example.myfridge.AlimentsOperations;
import com.example.myfridge.R;
import com.example.myfridge.ScanActivity;
import com.example.myfridge.databinding.FragmentHomeBinding;

import java.util.Vector;

public class HomeFragment extends Fragment {
    private AlimentsOperations alimentsOperations;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //affichage de la liste des aliments de la BDD dans le listView
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        ListView listViewAliment = (ListView) view.findViewById(R.id.listViewAliments);
        //création de l'objet permettant de modifier la BDD
        alimentsOperations = new AlimentsOperations(getContext());
        // On stocke dans le vecteur "lAlimentss" la liste des aliments
        // contenus dans la table "aliments" de la base de données
        Vector<Aliments> lAliments;
        alimentsOperations.open();
        lAliments = alimentsOperations.listAllAliments();
        alimentsOperations.close();
        // On associe au modèle de la ListView le vecteur de contacts
        // "lContacts"
        if (lAliments != null) {
            String[] anArrayString = new String[lAliments.size()];
            for (int i = 0; i < lAliments.size(); i++) {
                String s = lAliments.get(i).getId() + " - " +
                        lAliments.get(i).getNom_produit() + " : " +
                        lAliments.get(i).getDate_ajout();
                anArrayString[i] = s;
            }
            ArrayAdapter<String> listArrayAdapter;
            listArrayAdapter = new ArrayAdapter<String>(getContext(),
                   android.R.layout.simple_list_item_1,
                    anArrayString);

            //affichage de la BDD dans le listView
            listViewAliment.setAdapter(listArrayAdapter);
        }

        homeViewModel =new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}