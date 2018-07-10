package com.example.manon.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manon.bakingapp.Utils.JsonUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String myJson = JsonUtils.loadJsonFromAsset(getApplicationContext(), getString(R.string.json_filename));
        TextView txt = (TextView) findViewById(R.id.txtviewS);
        txt.setText(myJson);
    }
}
