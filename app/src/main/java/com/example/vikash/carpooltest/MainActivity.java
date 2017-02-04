package com.example.vikash.carpooltest;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText empIDText;
    private EditText passwordText;
    private Button logInButton;
    private Button signUpButton;
    private ProgressBar progressBar;
    private static final String LOGIN_URL = "https://carpooltest2017.herokuapp.com/users/signin";
    public static final String KEY_EMPID = "empID";
    public static final String KEY_PASSWORD = "password";
    //public static final String KEY_EMAIL = "email";
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        //emailTextView = (AutoCompleteTextView) findViewById(R.id.email);
        empIDText = (EditText)findViewById(R.id.empID);
        //progressBar = (ProgressBar) findViewById(R.id.login_progress);
        //passwordText = (EditText) findViewById(R.id.password);
        passwordText = (EditText)findViewById(R.id.password);
        //logInButton = (Button) findViewById(R.id.email_sign_in_button);
        logInButton = (Button)findViewById(R.id.btnLogin);
        logInButton.setOnClickListener(this);
        signUpButton = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnLogin) {
            Toast.makeText(getApplicationContext(), "Sign IN button Clicked", Toast.LENGTH_LONG).show();
            //progressBar.setVisibility(View.VISIBLE);
            try {
                if(isOnline()) {
                    getToken();
                }else{
                    Toast.makeText(MainActivity.this, "Please connect to network!", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(id == R.id.btnLinkToRegisterScreen){
            startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
        }
    }
    private void getToken() throws JSONException {
        String token = null;
        final String empID = empIDText.getText().toString().trim();
        final String password = passwordText.getText().toString().trim();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("empID", empID);
        jsonObject.put("password", password);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, LOGIN_URL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(MainActivity.this, "LogIn Succesfull", Toast.LENGTH_SHORT).show();
                        serializeToken(response);
                        Credentials credentials = getCredentials();
                        Bundle data = new Bundle();
                        data.putSerializable("credentials",credentials);
                        startActivity(new Intent(getApplicationContext(),RideChoiceActivity.class).putExtra("data",data));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "empID/password wrong \n Login failure", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);

    }
    public void serializeToken(JSONObject response){
        File file = new File(getFilesDir(),"credentials");
        Credentials credentials = null;
        try {
            credentials = new Credentials(response.getString("token"),response.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            objectOutputStream.writeObject(credentials);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //startActivity(new Intent(getApplicationContext(),ContactActivity.class));

    }
    private Credentials getCredentials(){
        File file = new File(getFilesDir(),"credentials");
        Credentials credentials = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            credentials = (Credentials) objectInputStream.readObject();
            objectInputStream.close();

        }catch(Exception e){

        }
        return credentials;
    }
    private boolean isOnline(){
        ConnectivityManager connectivityManager  = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            return  true;
        }
        return false;

    }
}
