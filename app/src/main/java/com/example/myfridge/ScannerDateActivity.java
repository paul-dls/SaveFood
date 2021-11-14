package com.example.myfridge;

import android.Manifest;
import android.annotation.SuppressLint;
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

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
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
                        Log.i("cemera",e.toString());
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
            Log.e("Main Activity", "Detector dependencies are not yet available");
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
        for (int i = 0; i < items.size(); i++)
        {
            TextBlock item = (TextBlock)items.valueAt(i);
            strBuilder.append(item.getValue());
            strBuilder.append("/");
            // The following Process is used to show how to use lines & elements as well
            for (int j = 0; j < items.size(); j++) {
                TextBlock textBlock = (TextBlock) items.valueAt(j);
                strBuilder.append(textBlock.getValue());
                strBuilder.append("/");
                for (Text line : textBlock.getComponents()) {
                    //extract scanned text lines here
                    Log.v("lines", line.getValue());
                    strBuilder.append(line.getValue());
                    strBuilder.append("/");
                    for (Text element : line.getComponents()) {
                        //extract scanned text words here
                        Log.v("element", element.getValue());
                        strBuilder.append(element.getValue());
                    }
                }
            }
        }
        Log.v("strBuilder.toString()", strBuilder.toString());

        txtView.post(new Runnable() {
            @Override
            public void run() {
                String rawDateString ="no date";
                String textRecup = strBuilder.toString();
                Log.i("erreur", "run est lancé");

                // compilation de la regex
                Pattern p = Pattern.compile("((0[1-9])|([1-2][0-9])|(30)|(31))[/:\\.\\- ]((0[1-9])|(1[0-2]))[/:\\.\\- ]((202[1-9])|(2[1-9]))");
                // création d'un moteur de recherche
                Matcher m = p.matcher(strBuilder);
                // lancement de la recherche de toutes les occurrences

                if (m.find()) {
                    rawDateString = m.group();
                }
                txtView.setText(rawDateString );
                try{
                    String strDatewithTime = "2015-08-04T10:11:30";
                    LocalDateTime aLDT = LocalDateTime.parse(strDatewithTime);
                }catch(DateTimeParseException e){
                    Log.i("erreur date paul", String.format("la date %d n'es pas valable", rawDateString));
                    rawDateString = String.format("la date %d n'es pas valable", rawDateString);
                }
                txtView.setText(rawDateString);
            }
        });
    }
}


