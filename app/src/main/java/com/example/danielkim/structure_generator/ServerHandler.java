package com.example.danielkim.structure_generator;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServerHandler {

    public static final String SERVER_ADDRESS = "http://dk.comxa.com/";
    public static final int CONNECTION_TIMEOUT = 1000 * 15;


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

    public void getUserFromDB(User user) {
        new GetUserAsyncTask(user).execute();
    }
    public class RegisterUserAsyncTask extends AsyncTask <Void, Void, Void> {
        User user;

        public RegisterUserAsyncTask(User user) {
            this.user = user;
        }

        @Override
        protected Void doInBackground(Void... params) {
            //Arraylist data server can read
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("first_name", user.fname));
            dataToSend.add(new BasicNameValuePair("last_name", user.lname));
            dataToSend.add(new BasicNameValuePair("email", user.email));
            dataToSend.add(new BasicNameValuePair("organization", user.org));
            dataToSend.add(new BasicNameValuePair("password", user.password));
            dataToSend.add(new BasicNameValuePair("confirm_password", user.confirmpassword));

            HttpParams httpRequestParams = getHttpRequestParams();

            //Setup client to make request to server
            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "Register.php");

            try {
                //Data URL encoded
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class GetUserAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        User returnedUser = null;

        public GetUserAsyncTask(User user) {
            this.user = user;
        }

        @Override
        protected User doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("email", user.email));
            dataToSend.add(new BasicNameValuePair("password", user.password));

            HttpParams httpRequestParams = getHttpRequestParams();

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "GetUserData.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                //Executing HttpPost gets a response; Store response in httpResponse
                HttpResponse httpResponse = client.execute(post);

                //Data from httpReponse
                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                System.out.println("result = " + result);
                //Read from JSON file and store in jObject
                //JSONArray jArray = new JSONArray(result);
                JSONObject jObject = new JSONObject(result);
                if (jObject.length() == 0) {
                    returnedUser = null;
                } else {

//                    String fname = jObject.getJSONArray(1).toString();
//                    String lname = jObject.getJSONArray(2).toString();
//                    String org = jObject.getJSONArray(4).toString();
//                    String confirmPass = jObject.getJSONArray(6).toString();

                    String first_name = jObject.getString("first_name");
                    String last_name = jObject.getString("last_name");
                    String organization = jObject.getString("Organization");
                    String confirm_password = jObject.getString("confirm_password");

                    returnedUser = new User(first_name, last_name, user.email, organization, user.password, confirm_password);
                }

            } catch (Exception e){
                e.printStackTrace();
            }
            return returnedUser;
        }
    }

    //Setup for http: set connection and timeout
    private HttpParams getHttpRequestParams() {
        HttpParams httpRequestParams = new BasicHttpParams();
        //Time to wait before post executed
        HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
        //Time to wait to receive from server
        HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
        return httpRequestParams;
    }
}
