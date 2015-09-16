package com.example.danielkim.structure_generator;

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

    public User (String email, String password) {
        this.email = email;
        this.password = password;
        this.fname = "";
        this.lname = "";
        this.org = "";
        this.confirmpassword = "";
    }

}
