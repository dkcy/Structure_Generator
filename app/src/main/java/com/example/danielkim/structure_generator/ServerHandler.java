package com.example.danielkim.structure_generator;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.util.ArrayList;

/**
 * Created by danielkim on 9/11/15.
 */
public class ServerHandler {

    public static final String SERVER_ADDRESS = "http://dk.comxa.com/";
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    User user;

//    ProgressDialog progressDialog;
//    public ServerHandler(Context context) {
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setTitle("Processing");
//        progressDialog.setMessage("Please wait...");
//        progressDialog.setCancelable(false);
//    }

    public void registerToDatabase(User user) {
        new RegisterUserAsyncTask(user).execute();
    }

    public class RegisterUserAsyncTask extends AsyncTask <Void, Void, Void> {
        User user;

        public RegisterUserAsyncTask(User user) {
            this.user = user;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("first_name", user.fname));
            dataToSend.add(new BasicNameValuePair("last_name", user.lname));
            dataToSend.add(new BasicNameValuePair("email", user.email));
            dataToSend.add(new BasicNameValuePair("organization", user.org));
            dataToSend.add(new BasicNameValuePair("password", user.password));
            dataToSend.add(new BasicNameValuePair("confirm_password", user.confirmpassword));

            HttpParams httpRequestParams = getHttpRequestParams();

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "Register.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private HttpParams getHttpRequestParams() {
        HttpParams httpRequestParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
        return httpRequestParams;
    }
}
