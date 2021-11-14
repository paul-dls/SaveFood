package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        Intent deMainActivity = getIntent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() == null) {
                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("MainActivity", "Scanned");

                //récupération code barre et appel de l'activité d'affichage du nom du produit
                String codebarre = Result.getContents();
                Toast.makeText(this, codebarre, Toast.LENGTH_SHORT).show();
                Intent VersgetInfo = new Intent();
                VersgetInfo.setClass(this,getInfoActivity.class);
                VersgetInfo.putExtra("codebarre",codebarre);
                startActivity(VersgetInfo);

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    // methode appelée lorsqu'on clique sur go SCAN pour initier le scanner
    public void onClickScanner(View view) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(ScanActivity.this);
        intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setPrompt("SCAN"); //Set a prompt to display on the capture screen, instead of using the default.
        intentIntegrator.setBarcodeImageEnabled(false); //set to true enables saving the barcode image and sending its paths in the result intent
        intentIntegrator.initiateScan();
    }
}