package com.example.danielkim.structure_generator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by danielkim on 9/10/15.
 */
public class Register extends AppCompatActivity implements View.OnClickListener{
    Button buttonRegister;
    EditText fname, lname, email, org, pass, confirmpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fname = (EditText) findViewById(R.id.register_fname_edit_text);
        lname = (EditText) findViewById(R.id.register_lname_edit_text);
        email = (EditText) findViewById(R.id.register_email_edit_text);
        org = (EditText) findViewById(R.id.register_org_edit_text);
        pass = (EditText) findViewById(R.id.register_password_edit_text);
        confirmpass = (EditText) findViewById(R.id.register_confirmpassword_edit_text);
        buttonRegister = (Button) findViewById(R.id.register_button);

        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_button:
                String fnameRegis = fname.getText().toString();
                String lnameRegis = lname.getText().toString();
                String emailRegis = email.getText().toString();
                String orgRegis = org.getText().toString();
                String passRegis = pass.getText().toString();
                String confirmpassRegis = confirmpass.getText().toString();

                User user = new User (fnameRegis, lnameRegis, emailRegis, orgRegis, passRegis, confirmpassRegis);
                registerUser(user);
                break;
        }
    }

    private void registerUser(User user){
        ServerHandler serverHandler = new ServerHandler();
//      ServerHandler serverHandler = new ServerHandler(this);
        serverHandler.registerToDatabase(user);
    }
}
