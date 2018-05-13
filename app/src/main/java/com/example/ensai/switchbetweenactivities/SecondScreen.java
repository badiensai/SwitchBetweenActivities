package com.example.ensai.switchbetweenactivities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;

import java.net.MalformedURLException;
import java.net.URL;

import xdroid.toaster.Toaster;

import static com.facebook.stetho.inspector.network.ResponseHandlingInputStream.TAG;
/*
public class SecondScreen extends Activity {

    public static TextView txt;
    public static double xArret;
    public static double y;
    public static URL url;

    /**
     * Called when the activity is first created.
     */
    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);
        

        final TextView txtArret = (TextView) findViewById(R.id.txtArret);
        final TextView txtBus = (TextView) findViewById(R.id.txtBus);
        txt = (TextView) findViewById(R.id.textView2);

        Button btnClose = (Button) findViewById(R.id.btnClose);

        Intent i = getIntent();
        // Receiving the Data
        String arret = i.getStringExtra("arret");
        String bus = i.getStringExtra("bus");
        Log.e("Second Screen", arret + "." + bus);

        // Displaying Received data
        txtArret.setText(arret);
        txtBus.setText(bus);

        Toaster.toast(arret);

        //Requête HTTP avec l'arrêt correspondant choisit par l'utilisateur
        Uri uri = Uri.parse("https://data.explore.star.fr/api/records/1.0/search/" +
                "?dataset=tco-bus-topologie-pointsarret-td&sort=nom&facet=nom")
                .buildUpon()
                .appendQueryParameter("refine.nom", arret)
                .build();



        try {
            url = new URL(uri.toString());

        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        // Binding Click event to Button
        /*btnClose.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                fetchData process = new fetchData(this);
                process.execute();

                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), MapsActivity.class);

                //Sending data to another Activity
                nextScreen.putExtra("xArret", xArret);
                nextScreen.putExtra("y", y);

                //Log.e("n", xArret+"."+ y +"erreurs coordonnées");

                startActivity(nextScreen);

                //finish();
            }
        });

    }
}*/