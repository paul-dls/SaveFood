package com.example.myfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AjoutManuelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_manuel);
    }

    public void EntreeCodebarre(View view) {
        EditText editCodebarre = findViewById(R.id.editCodebarre);
        String codebarre = editCodebarre.getText().toString();
        Intent versgetInfo = new Intent();
        versgetInfo.setClass(this,getInfoActivity.class);
        versgetInfo.putExtra("codebarre",codebarre);
        startActivity(versgetInfo);
    }
}