package com.example.ensai.switchbetweenactivities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;

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

        ArrayAdapter<String> adapterArret =
               new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrets);

        inputArret.setAdapter(adapterArret);
        inputArret.setThreshold(1);

        inputStream = getResources().openRawResource(R.raw.lignesbus);
        csvFile = new CSVFile(inputStream);
        String[] lignes = csvFile.read(1).split(";");
        lignes = new HashSet<String>(Arrays.asList(lignes)).toArray(new String[0]);

        ArrayAdapter<String> adapterBus = new
                ArrayAdapter<>(this,android.R.layout.simple_list_item_1,lignes);

        inputBus.setAdapter(adapterBus);
        inputBus.setThreshold(1);

        inputStream = getResources().openRawResource(R.raw.parcours);
        csvFile = new CSVFile(inputStream);
        String[] destss = csvFile.read(0).split(";");
        for(int l=0;l<destss.length;l++){
            destss[l]=destss[l].split(" ")[0];
        }
        destss = new HashSet<String>(Arrays.asList(destss)).toArray(new String[0]);


        ArrayAdapter<String> adapterDest = new
                ArrayAdapter<>(this,android.R.layout.simple_list_item_1,destss);

        inputDestination.setAdapter(adapterDest);
        inputDestination.setThreshold(1);



        //Listening to button event
        btnNextScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

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

