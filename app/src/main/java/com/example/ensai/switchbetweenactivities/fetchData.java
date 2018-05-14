package com.example.ensai.switchbetweenactivities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;

import static xdroid.core.Global.getResources;

/**
 * Created by Abhishek Panwar.
 */


public class fetchData extends AsyncTask<Void,Void,Void> {
    Activity a;
    String data ="",data2="",data3="",coordBus="";
    double xArret =0.0,yArret=0.0,xBus=0.0,yBus=0.0, xDestination =0.0, yDestination =0.0;
    String arret, bus, destination;



    public fetchData(Activity acti, String art, String dest, String bu){
        this.a = acti;
        arret=art;
        destination=dest;
        bus=bu;
    }

    double distance(double x1, double y1, double x2, double y2){
        return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //Requête HTTP avec l'arrêt correspondant choisit par l'utilisateur
            Uri uri = Uri.parse("https://data.explore.star.fr/api/records/1.0/search/" +
                    "?dataset=tco-bus-topologie-pointsarret-td&sort=nom&facet=nom")
                    .buildUpon()
                    .appendQueryParameter("refine.nom", arret)
                    .build();
            Uri uri2 =  Uri.parse("https://data.explore.star.fr/api/records/1.0/search/" +
                    "?dataset=tco-bus-vehicules-position-tr&sort=idbus&facet=numerobus&facet" +
                    "=etat&facet=nomcourtligne&facet=sens&facet=destination" )
                    .buildUpon()
                    .appendQueryParameter("refine.nomcourtligne", bus)
                    .build();

            Uri uri3 = Uri.parse("https://data.explore.star.fr/api/records/1.0/search/" +
                    "?dataset=tco-bus-topologie-parcours-td&sort=idligne&facet=nomcourtligne" +
                    "&facet=senscommercial&facet=type&facet=nomarretdepart" +
                    "&facet=nomarretarrivee&facet=estaccessiblepmr")
                    .buildUpon()
                    .appendQueryParameter("refine.nomcourtligne", destination)
                    .build();

                URL url = new URL(uri.toString());
                URL url2 = new URL(uri2.toString());
                URL url3 = new URL(uri3.toString());



                //String toast=url.toString();
                //Toaster.toast(toast);


            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }

            //Récupérer les coordoonnées GPS de l'arrêt
            JSONObject JO = new JSONObject(data);
            JSONArray records = JO.getJSONArray("records");
            JSONObject record = (JSONObject) records.get(0);
            JSONObject fields = record.getJSONObject("fields");
            JSONArray coordonnees = fields.getJSONArray("coordonnees");
            xArret = coordonnees.getDouble(0);
            yArret = coordonnees.getDouble(1); //TODO : choisir le bon arrêt (destination)


            httpURLConnection = (HttpURLConnection) url2.openConnection();
            inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data2 = data2 + line;
            }


            httpURLConnection = (HttpURLConnection) url3.openConnection();
            inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data3 = data3 + line;
            }

            //Récupérer les coordoonnées GPS de l'arrêt de destination
            /*inputStream = getResources().openRawResource(R.raw.destination);
            CSVFile csvFile = new CSVFile(inputStream);
            String arretDestination=csvFile.match(inputStream,0,1,bus,destination,3);
                //La destination ne correspond pas à un arrêt particulier, on en choisit un sur la ligne

            uri = Uri.parse("https://data.explore.star.fr/api/records/1.0/search/" +
                    "?dataset=tco-bus-topologie-pointsarret-td&sort=nom&facet=nom")
                    .buildUpon()
                    .appendQueryParameter("refine.nom", arretDestination)
                    .build();

            httpURLConnection = (HttpURLConnection) url.openConnection();
            inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }
            JO = new JSONObject(data);
            records = JO.getJSONArray("records");
            record = (JSONObject) records.get(0);
            fields = record.getJSONObject("fields");
             coordonnees = fields.getJSONArray("coordonnees");
            xDestination = coordonnees.getDouble(0);
            yDestination = coordonnees.getDouble(1);
            */



            JO = new JSONObject(data3);
            int m=JO.getInt("nhits");
            records = JO.getJSONArray("records");
            int j=-1;
            String libellelong="";
            while(j<m&&!libellelong.split(" ")[0].equals(destination)){
                j++;
                record = (JSONObject) records.get(j);
                fields = record.getJSONObject("fields");
                libellelong = fields.getString("libellelong");
            }


            //Coordoonnées de l'arrêt de destination
            coordonnees = fields.getJSONArray("geo_point_2d");
            xDestination =coordonnees.getDouble(0);
            yDestination = coordonnees.getDouble(1);


            //coordoonnées GPS du bus le plus proche
            JO = new JSONObject(data2);
            int n=JO.getInt("nhits");
            records = JO.getJSONArray("records");
            String etat,dest;

            Double xTemp=0.0,yTemp=0.0;

            for (int i=0;i<n; i++) {
                record = (JSONObject) records.get(i);
                fields = record.getJSONObject("fields");
                etat = fields.getString("etat");
                dest = fields.getString("destination");

                if (etat.equals("En ligne")&&dest.equals(this.destination)) {
                    coordonnees = fields.getJSONArray("coordonnees");
                    //Coordonnées du bus examiné
                    xTemp = coordonnees.getDouble(0);
                    yTemp = coordonnees.getDouble(1);

                    if(//Distance entre le bus et l'arrêt la plus petite
                            distance(xArret,yArret,xTemp,yTemp)<distance(xArret,yArret,xBus,yBus)
                                    &&
                                    //Distance entre le bus et l'arrêt de destination plus grande
                                    //que celle entre l'arrêt de départ et l'arrêt traité
                                    //i.e. le bus n'a pas encore dépassé cet arrêt
                                    distance(xDestination,yDestination,xTemp,yTemp)
                                            >distance(xDestination,yDestination,xArret,yArret)
                            ){
                        xBus=xTemp;
                        yBus=yTemp;
                    }
                }
            }

            } catch (MalformedURLException e){
                e.printStackTrace();

        }catch(IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);

            //Starting a new Intent
            Intent nextScreen = new Intent(a.getApplicationContext(), MapsActivity.class);

            //Sending data to another Activity
            nextScreen.putExtra("coordx", xArret);
            nextScreen.putExtra("coordy", yArret);
            nextScreen.putExtra("arret", this.arret);
            nextScreen.putExtra("coordBusx", xBus);
            nextScreen.putExtra("coordBusy", yBus);
            nextScreen.putExtra("bus", bus);
        nextScreen.putExtra("data", data);
            //Log.e("n", inputArret.getText()+"."+ inputBus.getText());


            a.startActivity(nextScreen);

            //finish();


        }





}
