package com.example.danielkim.structure_generator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;


public class CreateRoster extends ActionBarActivity {
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_roster);

        spinner = (Spinner)findViewById(R.id.spinner);

    }


}
