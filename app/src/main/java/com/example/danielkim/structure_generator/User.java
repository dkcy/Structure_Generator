package com.example.danielkim.structure_generator;

/**
 * Created by danielkim on 9/10/15.
 */
public class User {
    String fname, lname, email, org, password, confirmpassword;

    public User (String fname, String lname, String email, String org,
                                String password, String confirmpassword) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.org = org;
        this.password = password;
        this.confirmpassword = confirmpassword;
    }


}
