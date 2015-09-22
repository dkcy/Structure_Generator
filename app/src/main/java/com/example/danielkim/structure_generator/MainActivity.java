package com.example.danielkim.structure_generator;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Callback {

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

                break;

            case R.id.register_link:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }

    private void connectUserDB(User user) {
        ServerHandler serverHandler = new ServerHandler();
        serverHandler.getUserFromDB(user, this);
        }

    @Override
    public void getCallback(User returnedUser) {

        if (returnedUser == null) {
            showErrorPopup();
        } else {
            System.out.println("Logged in user " + returnedUser.email + " " + "pw: " + returnedUser.password);
            logUserToLocalDB(returnedUser);
        }
    }

    private void showErrorPopup() {
        AlertDialog.Builder popup = new AlertDialog.Builder(this);
        popup.setMessage("Incorrect username and password");
        popup.setPositiveButton("OK", null);
        popup.show();
    }

    private void logUserToLocalDB(User user) {
        userLocalDB.storeUserDetails(user);
        startActivity(new Intent(this, Home.class));
    }

}
