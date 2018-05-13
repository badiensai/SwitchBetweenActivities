package com.example.ensai.switchbetweenactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.widget.Toast;

import xdroid.toaster.Toaster;

public class Main2Activity extends AppCompatActivity {

    ImageButton busButton, metroButton, veloButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        addListenerOnButton();

    }

    public void addListenerOnButton() {


        busButton = (ImageButton) findViewById(R.id.busButton);



        busButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Toaster.toast("bus");
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(nextScreen);


            }

        });

        veloButton = (ImageButton) findViewById(R.id.veloButton);

        veloButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Toaster.toast("Velo");
            }

        });

        metroButton = (ImageButton) findViewById(R.id.metroButton);

        metroButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Toaster.toast("metro");
            }



        });

    }


}
