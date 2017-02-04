package com.example.vikash.carpooltest;

import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText empIDText;
    private EditText nameText;
    private EditText phoneText;
    private EditText passwordText;
    private EditText repasswordText;
    private Button btnRegister;
    private Button btnLongIn;

    private static final String REGISTER_URL = "https://carpooltest2017.herokuapp.com/users";
    private static final String REGISTER_URL_CHECK = "https://carpooltest2017.herokuapp.com/check/users";
    public static final String KEY_EMPID = "empID";
    public static final String KEY_PASSWORD = "password";
    //public static final String KEY_EMAIL = "email";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        empIDText = (EditText)findViewById(R.id.empID);
        nameText = (EditText)findViewById(R.id.name);
        phoneText = (EditText) findViewById(R.id.phone);
        passwordText = (EditText) findViewById(R.id.password);
        repasswordText = (EditText) findViewById(R.id.repassword);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        btnLongIn = (Button) findViewById(R.id.btnLogin);
        btnLongIn.setOnClickListener(this);
    }

    void checkUser() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("empID",empIDText.getText().toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, REGISTER_URL_CHECK, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(SignUpActivity.this, "No User Found", Toast.LENGTH_SHORT).show();
                        try {
                            registerUser();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUpActivity.this, "User Found", Toast.LENGTH_SHORT).show();
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

    void registerUser() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("empID",empIDText.getText().toString());
        jsonObject.put("name",nameText.getText().toString());
        jsonObject.put("phone",phoneText.getText().toString());
        jsonObject.put("password",passwordText.getText().toString());
        JSONObject user = new JSONObject();
        user.put("user",jsonObject);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, REGISTER_URL, user,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(SignUpActivity.this, "Registered!Login Now", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUpActivity.this, "Unable to register"+ error, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.btnRegister){
            try {
                checkUser();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
