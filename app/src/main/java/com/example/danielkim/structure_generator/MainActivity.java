package com.example.danielkim.structure_generator;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginButton;
    EditText email, password;
    TextView registerLink;
    UserLocalDB userLocalDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.login_email_edit_text);
        password = (EditText) findViewById(R.id.login_password_edit_text);
        loginButton = (Button) findViewById(R.id.login_button);
        registerLink = (TextView) findViewById(R.id.register_link);

        loginButton.setOnClickListener(this);
        registerLink.setOnClickListener(this);

        userLocalDB = new UserLocalDB(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                String email1 = email.getText().toString();
                String password1 = password.getText().toString();

                User user = new User(email1, password1);
                connectUserDB(user);
                //new AttemptLogin(email1, password1);

//                userLocalDB.storeData(user);
//                userLocalDB.setLogin(true);
                break;

            case R.id.register_link:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }

    private void connectUserDB(User user) {
        ServerHandler serverHandler = new ServerHandler();
        serverHandler.getUserFromDB(user);
        if (user == null) {
            showErrorPopup();
        } else {
            System.out.println("Logged in user");
            logInUser(user);
        }
    }

    private void showErrorPopup() {
        AlertDialog.Builder popup = new AlertDialog.Builder(MainActivity.this);
        popup.setMessage("Incorrect username and password");
        popup.setPositiveButton("OK", null);
        popup.show();
    }

    private void logInUser(User user) {
        userLocalDB.storeData(user);
        userLocalDB.setLogin(true);
        startActivity(new Intent(this, Home.class));

    }
//    private class AttemptLogin extends AsyncTask<Void, Void, String> {
//        String username, password;
//        public AttemptLogin(String username, String password) {
//            this.username = username;
//            this.password = password;
//        }
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//            return null;
//        }
//    }
}
