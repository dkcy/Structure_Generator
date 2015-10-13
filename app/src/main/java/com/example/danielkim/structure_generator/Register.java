package com.example.danielkim.structure_generator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements View.OnClickListener, RegisterResponse{
    Button buttonRegister;
    EditText fname, lname, email, org, pass, confirmpass;
    private Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    private Matcher matcher;
    private boolean dupEmail;
    //private static final String EMAIL_REGEX = ;
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
        //AlertDialog.Builder alert2 = new AlertDialog.Builder(this);

        switch (v.getId()){
            case R.id.register_button:
                String fnameRegis = fname.getText().toString();
                String lnameRegis = lname.getText().toString();
                String emailRegis = email.getText().toString();
                String orgRegis = org.getText().toString();
                String passRegis = pass.getText().toString();
                String confirmpassRegis = confirmpass.getText().toString();

                if (TextUtils.isEmpty(fnameRegis) || TextUtils.isEmpty(lnameRegis) || TextUtils.isEmpty(emailRegis) ||
                        TextUtils.isEmpty(orgRegis) || TextUtils.isEmpty(passRegis) || TextUtils.isEmpty(confirmpassRegis)) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Incomplete form");
                    alert.setMessage("Please complete all fields");
                    alert.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alert.setCancelable(true);
                    alert.create();
                    alert.show();
                } else if (!passRegis.equals(confirmpassRegis)) {
                    AlertDialog.Builder alert2 = new AlertDialog.Builder(this);
                    alert2.setTitle("Password Error");
                    alert2.setMessage("Password does not match");
                    alert2.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alert2.setCancelable(true);
                    alert2.create();
                    alert2.show();
                } else if (!validateEmail(emailRegis)){
                    AlertDialog.Builder alert2 = new AlertDialog.Builder(this);
                    alert2.setTitle("Invalid Email");
                    alert2.setMessage("Please enter a valid email");
                    alert2.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alert2.setCancelable(true);
                    alert2.create();
                    alert2.show();
                } else {
                    User user = new User(fnameRegis, lnameRegis, emailRegis, orgRegis, passRegis, confirmpassRegis);
                    registerUser(user);
//                    break;
                }
                if (getDupEmail() != true) {
                    AlertDialog.Builder popup = new AlertDialog.Builder(this);
                    popup.setTitle("Invalid Email");
                    popup.setMessage("Email already exists");
                    popup.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    popup.setCancelable(true);
                    popup.create();
                    popup.show();
                    System.out.println("%%%%%%%");
                } else {
                    startActivity(new Intent(this, MainActivity.class));
                }
        }
    }

    private void registerUser(User user){
        ServerHandler serverHandler = new ServerHandler(this);
        serverHandler.registerToDatabase(user, this);
    }

    private boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void getRegisterResponse(String num) {
        String x = num.substring(0,1);
        if (x.equals("2")) {
            System.out.println("11111111 " + dupEmail);
            setDupEmail();
            System.out.println("22222222 " + dupEmail);

        } else {
            System.out.println("@@@@@@@@@ " + num + "......");
        }
    }

    public boolean setDupEmail() {
        this.dupEmail = true;
        return dupEmail;
    }

    public boolean getDupEmail() {
        return dupEmail;
    }
}
