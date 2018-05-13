package com.example.ensai.switchbetweenactivities;

import android.content.Intent;
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
            URL url = MainActivity.url;

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }

            /*url = MainActivity.url2;

            httpURLConnection = (HttpURLConnection) url.openConnection();
            inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data2 = data2 + line;
            }

            url = MainActivity.url3;

            httpURLConnection = (HttpURLConnection) url.openConnection();
            inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data3 = data3 + line;*
            }*/
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);

        try {
            //Récupérer les coordoonnées GPS de l'arrêt
            JSONObject JO = new JSONObject(data);
            /*JSONArray records = JO.getJSONArray("records");
            JSONObject record = (JSONObject) records.get(0);
            JSONObject fields = record.getJSONObject("fields");
            JSONArray coordonnees = fields.getJSONArray("coordonnees");
            xArret = coordonnees.getDouble(0);
            yArret = coordonnees.getDouble(1); //TODO : choisir le bon arrêt (destination)


            //Récupérer les coordoonnées GPS de l'arrêt de destination
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



            coordonnees = fields.getJSONArray("geo_point_2d");
            xDestination =coordonnees.getDouble(0);
            yDestination = coordonnees.getDouble(1);

            //Récupérer les coordoonnées GPS du bus le plus proche
            JO = new JSONObject(data2);
            int n=JO.getInt("nhits");
            records = JO.getJSONArray("records");
            String etat;

            Double xTemp=0.0,yTemp=0.0;

            for (int i=0;i<n; i++) {
                record = (JSONObject) records.get(i);
                fields = record.getJSONObject("fields");
                etat = fields.getString("etat");

                if (etat.equals("En ligne")&&fields.get("destination").equals(this.destination)) {
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
                    //TODO : choisir les bons bus (bonne destination)
                }
            }
        */

        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            //Starting a new Intent
            Intent nextScreen = new Intent(a.getApplicationContext(), MapsActivity.class);

            //Sending data to another Activity
            nextScreen.putExtra("coordx", xArret);
            nextScreen.putExtra("coordy", yArret);
            nextScreen.putExtra("arret", this.arret);
            nextScreen.putExtra("coordBusx", xBus);
            nextScreen.putExtra("coordBusy", yBus);
            nextScreen.putExtra("bus", bus);

            //Log.e("n", inputArret.getText()+"."+ inputBus.getText());


            a.startActivity(nextScreen);

            //finish();


        }





    }
}