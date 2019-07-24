package com.mc.carrent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private TextInputLayout textInputLayoutEmail, textInputLayoutPassword;
    private ProgressBar progressBar;
    private MaterialButton materialButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textInputLayoutEmail = findViewById(R.id.textInputLayoutLoginEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutLoginPassword);
        materialButton = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBarLogin);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void onRegister(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void onLogin(View view) {
        int flag = 0;

        String email = textInputLayoutEmail.getEditText().getText().toString().trim();
        String password = textInputLayoutPassword.getEditText().getText().toString().trim();

        if (validateEmail(email)) {
            textInputLayoutEmail.setErrorEnabled(false);
            textInputLayoutEmail.setError(null);
            flag = flag + 1;
        } else {
            textInputLayoutEmail.setError("Please Enter a Valid Email");
        }

        if (validatePassword(password)) {
            textInputLayoutPassword.setErrorEnabled(false);
            textInputLayoutPassword.setError(null);
            flag = flag + 1;

        } else {
            textInputLayoutPassword.setError("Please enter a valid password");
        }

        if (flag == 2) {
            progressBar.setVisibility(View.VISIBLE);
            materialButton.setText("");
            materialButton.setEnabled(false);

            Map<String, String> requ = new HashMap<String, String>();
            requ.put("email", email);
            requ.put("password", password);

            String url = Url.loginUrl;

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(requ), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String userId = response.getString("userId");
                        String email = response.getString("email");

                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userId", userId);
                        editor.putString("email", email);
                        editor.commit();

                        progressBar.setVisibility(View.INVISIBLE);
                        materialButton.setText("Login");
                        materialButton.setEnabled(true);
                        Intent intent = new Intent(Login.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    if (error instanceof NetworkError || error instanceof NoConnectionError) {
                        Toast.makeText(Login.this, "Please Check your Internet Connection!!!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        materialButton.setText("Login");
                        materialButton.setEnabled(true);

                    } else {
                        String statuscode = String.valueOf(error.networkResponse.statusCode);
                        if (statuscode.equals("401")) {
                            Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            materialButton.setText("Login");
                            materialButton.setEnabled(true);
                        }
                    }

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            SingletonRequest.getInstance(this.getApplicationContext()).addtoRequestQueue(request);


        }
    }


    private boolean validateEmail(String email) {
        if (email.isEmpty() || email.length() > 50) {
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePassword(String password) {
        if (password.isEmpty() || password.length() > 50) {
            return false;
        } else {
            return true;
        }
    }

}
