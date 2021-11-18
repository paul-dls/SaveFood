package com.example.myfridge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfridge.databinding.ActivityNavigationDrawerBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NavigationDrawerActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationDrawerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNavigationDrawer.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_drawer);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public void ajouter(View view) {
        // récupération date d'ajout
        //Log.i("erreur paul drawer","lacement de la fonction ajouter");
        Calendar calendrier=Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM -yyyy");
        String dateAjout= df.format(calendrier.getTime());
        Log.i("fonctionnement normal", "date d'ajout récupérée");
        Log.i("scan", "rentre dans le onClick");
        Intent VersAjoutAliment= new Intent();
        VersAjoutAliment.setClass(this, AjoutAliment.class);
        VersAjoutAliment.putExtra("aliment", new Aliments("1","non communiqué",dateAjout,"non communiqué",1));

        startActivity(VersAjoutAliment);
        Log.i("scan", "intent vers AjoutAliment");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_drawer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



}