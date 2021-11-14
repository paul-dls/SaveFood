package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ajouter(View view) {
        Log.i("scan", "rentre dans le onClick");
        Intent VersScanActivity= new Intent();
        VersScanActivity.setClass(this, ScanActivity.class);
        VersScanActivity.putExtra("message", "ok");
        startActivity(VersScanActivity);
        Log.i("scan", "intent vers ScanActivity");
    }
}