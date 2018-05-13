package com.example.ensai.switchbetweenactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    public static TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         txt = (TextView) findViewById(R.id.textView);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
    }
}
