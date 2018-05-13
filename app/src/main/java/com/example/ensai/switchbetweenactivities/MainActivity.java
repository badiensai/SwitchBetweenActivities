package com.example.ensai.switchbetweenactivities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;

import xdroid.toaster.Toaster;

//https://www.androidhive.info/
public class MainActivity extends Activity {
    public EditText getInputArret() {
        return inputArret;
    }

    public void setInputArret(AutoCompleteTextView inputArret) {
        this.inputArret = inputArret;
    }

    // Initializing variables
    AutoCompleteTextView inputArret, inputBus, inputDestination;
    Button btnNextScreen;

    public static Double x=1.0,y=1.0;
    public static URL url,url2,url3;
    private Activity activ=this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        inputArret = (AutoCompleteTextView) findViewById(R.id.arret);
        inputBus = (AutoCompleteTextView) findViewById(R.id.bus);
        inputDestination = (AutoCompleteTextView) findViewById(R.id.destination);
        btnNextScreen = (Button) findViewById(R.id.btnNextScreen);

        InputStream inputStream = getResources().openRawResource(R.raw.pointsarret);
        CSVFile csvFile = new CSVFile(inputStream);
        String[] arrets = csvFile.read(2).split(";");
        arrets = new HashSet<String>(Arrays.asList(arrets)).toArray(new String[0]);

        ArrayAdapter adapterArret = new
                ArrayAdapter(this,android.R.layout.simple_list_item_1,arrets);

        inputArret.setAdapter(adapterArret);
        inputArret.setThreshold(1);

        inputStream = getResources().openRawResource(R.raw.lignesbus);
        csvFile = new CSVFile(inputStream);
        String[] lignes = csvFile.read(1).split(";");
        lignes = new HashSet<String>(Arrays.asList(lignes)).toArray(new String[0]);

        ArrayAdapter adapterBus = new
                ArrayAdapter(this,android.R.layout.simple_list_item_1,lignes);

        inputBus.setAdapter(adapterBus);
        inputBus.setThreshold(1);

        inputStream = getResources().openRawResource(R.raw.parcours);
        csvFile = new CSVFile(inputStream);
        String[] destss = csvFile.read(0).split(";");
        for(int l=0;l<destss.length;l++){
            destss[l]=destss[l].split(" ")[0];
        }
        destss = new HashSet<String>(Arrays.asList(destss)).toArray(new String[0]);


        ArrayAdapter adapterDest = new
                ArrayAdapter(this,android.R.layout.simple_list_item_1,destss);

        inputDestination.setAdapter(adapterDest);
        inputDestination.setThreshold(1);



        //Listening to button event
        btnNextScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {


                //Requête HTTP avec l'arrêt correspondant choisit par l'utilisateur
                Uri uri = Uri.parse("https://data.explore.star.fr/api/records/1.0/search/" +
                        "?dataset=tco-bus-topologie-pointsarret-td&sort=nom&facet=nom")
                        .buildUpon()
                        .appendQueryParameter("refine.nom", inputArret.getText().toString())
                        .build();
                Uri uri2 =  Uri.parse("https://data.explore.star.fr/api/records/1.0/search/" +
                        "?dataset=tco-bus-vehicules-position-tr&sort=idbus&facet=numerobus&facet" +
                        "=etat&facet=nomcourtligne&facet=sens&facet=destination" )
                        .buildUpon()
                        .appendQueryParameter("refine.nomcourtligne", inputBus.getText().toString())
                        .build();

                Uri uri3 = Uri.parse("https://data.explore.star.fr/api/records/1.0/search/" +
                        "?dataset=tco-bus-topologie-parcours-td&sort=idligne&facet=nomcourtligne" +
                        "&facet=senscommercial&facet=type&facet=nomarretdepart" +
                        "&facet=nomarretarrivee&facet=estaccessiblepmr")
                        .buildUpon()
                        .appendQueryParameter("refine.nomcourtligne", inputBus.getText().toString())
                        .build();
                try {
                    url = new URL(uri.toString());
                    url2 = new URL(uri2.toString());
                    url3 = new URL(uri3.toString());

                    //String toast=url.toString();
                    //Toaster.toast(toast);
                } catch (MalformedURLException e){
                    e.printStackTrace();
                }

                /*try{
                    Thread.sleep(10000);
                }catch(InterruptedException e){};*/

                fetchData process = new fetchData(activ,inputArret.getText().toString(),
                        inputDestination.getText().toString(),
                        inputBus.getText().toString());
                process.execute();



            }
        });
    }
}

