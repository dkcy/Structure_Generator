package com.example.danielkim.structure_generator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Home extends AppCompatActivity implements View.OnClickListener {

    TextView loggedUserFN;
    Button bCreate, bEdit, bLogout;
    UserLocalDB userLocalDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loggedUserFN = (TextView) findViewById(R.id.loggedInUser);

        bCreate = (Button) findViewById(R.id.createroster_button);
        bEdit = (Button) findViewById(R.id.editroster_button);

        bLogout = (Button) findViewById(R.id.logout_button);

        bLogout.setOnClickListener(this);
        userLocalDB = new UserLocalDB(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (userLocalDB.getLoggedUser() != null) {
            displayUser();
        } else {
            startActivity(new Intent(Home.this, MainActivity.class));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout_button:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.createroster_button:
                startActivity(new Intent(this, CreateRoster.class));
                break;
            case R.id.editroster_button:
                //startActivity(new Intent(this, EditRoster.class));
                break;
        }
    }

    private void displayUser() {
        User user = userLocalDB.getLoggedUser();
        loggedUserFN.setText(user.fname + "!");
    }
}
