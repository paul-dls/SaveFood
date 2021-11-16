package com.example.myfridge;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.myfridge.ui.home.AjoutAliment;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScannerDateActivity extends AppCompatActivity implements SurfaceHolder.Callback, Detector.Processor {

    private SurfaceView cameraView;
    private TextView txtView;
    private CameraSource cameraSource;

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (Exception e) {
                        Log.i("erreur cemera date paul",e.toString());
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_date);
        cameraView = findViewById(R.id.surface_view);
        txtView = findViewById(R.id.txtview);
        TextRecognizer txtRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!txtRecognizer.isOperational()) {
            Log.e("erreur camera date paul", "les dependencies du detecteur de text ne sont pas encore disponibles");
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), txtRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(this);
            txtRecognizer.setProcessor(this);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},1);
                return;
            }
            cameraSource.start(cameraView.getHolder());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        cameraSource.stop();
    }

    @Override
    public void release() {

    }

    @Override
    public void receiveDetections(Detector.Detections detections) {
        SparseArray items = detections.getDetectedItems();
        final StringBuilder strBuilder = new StringBuilder();

        for (int j = 0; j < items.size(); j++) {
            TextBlock textBlock = (TextBlock) items.valueAt(j);

            //on recupère les paragraphes (séparées par des *)
            //strBuilder.append(textBlock.getValue());
            strBuilder.append("*");

            for (Text line : textBlock.getComponents()) {
                //on recupère les lignes (séparées par des _)
                //Log.v("fontionnement normal camera date paul, lines", line.getValue());
                //strBuilder.append(line.getValue());
                strBuilder.append("_");
                for (Text element : line.getComponents()) {
                    //on recupère les mots
                    Log.v("normal camera date paul", element.getValue());
                    strBuilder.append(element.getValue());
                    strBuilder.append(" ");
                }
            }
        }

        Log.v("normal camera date paul", strBuilder.toString());

        txtView.post(new Runnable() {
            @Override
            public void run() {
                String rawDateString ="no date";
                String textRecup = strBuilder.toString();// textge brut récupéré
                Log.v("normal date paul", "textRecup        " +textRecup);

                //on le met au format iso
                textRecup = textRecup.replaceAll("[\\. /:]", "\\-");
                Log.i("normal date paul", "textRecupModifié = " +textRecup);

                // on créé un moteur de recherche de date
                Pattern p = Pattern.compile("((0[1-9])|([1-2][0-9])|(30)|(31))\\-((0[1-9])|(1[0-2]))\\-((202[1-9])|(2[1-9]))");
                Matcher m = p.matcher(textRecup);

                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                //to do, utiliser un while et cecker plusieurs date si la premiere declanche une erreur
                if (m.find()) {
                    Log.v("normal date paul","au moins un mot du format date trouvé");
                    rawDateString = m.group();
                    try {
                        Date datePeremption = format.parse(rawDateString);
                    } catch (ParseException e) {
                        Log.i("erreur date paul", String.format("la date %s n'es pas valable", rawDateString));
                        Log.i("erreur date paul", String.valueOf(e));
                    }
                }
                Intent versAjoutAliment = new Intent();
                versAjoutAliment.setClass(getBaseContext(), AjoutAliment.class);
                versAjoutAliment.putExtra("DatePeremption", rawDateString);
                startActivity(versAjoutAliment);
                txtView.setText(rawDateString);

            }
        });
    }
}


