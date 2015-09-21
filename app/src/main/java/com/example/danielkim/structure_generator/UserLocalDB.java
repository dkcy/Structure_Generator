package com.example.danielkim.structure_generator;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalDB {
    public static final String NAME = "loggedInUserDetails";
    SharedPreferences sp;

    public UserLocalDB(Context context) {
        sp = context.getSharedPreferences(NAME, 0);
    }

    public void storeUserDetails (User user) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("fname", user.fname);
        editor.putString("lname", user.lname);
        editor.putString("email", user.email);
        editor.putString("org", user.org);
        editor.putString("pass", user.password);
        editor.putString("confirmPass", user.confirmpassword);
        editor.commit();
    }

    public User getLoggedUser() {
        String fname =sp.getString("fname", "");
        String lname =sp.getString("lname", "");
        String email =sp.getString("email", "");
        String org =sp.getString("org", "");
        String pass =sp.getString("pass", "");
        String confirmPass =sp.getString("confirmPass", "");

        User localUser = new User (fname, lname, email, org, pass, confirmPass);

        return localUser;
    }
}
