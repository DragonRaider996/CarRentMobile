package com.mc.carrent.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.mc.carrent.R;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputLayout textInputLayoutEmail, textInputLayoutPassword, textInputLayoutName, textInputLayoutContact, textInputLayoutCity;
    private AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmailRegister);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPasswordRegister);
        textInputLayoutName = findViewById(R.id.textInputLayoutNameRegister);
        textInputLayoutContact = findViewById(R.id.textInputLayoutContactRegister);
        textInputLayoutCity = findViewById(R.id.textInputLayoutCity);
        toolbar = findViewById(R.id.toolbarRegister);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextViewRegisterCity);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String[] cities = new String[]{"Halifax", "Dartmouth", "Sydney", "Truro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.dropdown_city, cities);

        autoCompleteTextView.setAdapter(adapter);
    }

    //Handling the register click
    public void onRegister(View view) {
        int flag = 0;

        String email = textInputLayoutEmail.getEditText().getText().toString().trim();
        String password = textInputLayoutPassword.getEditText().getText().toString().trim();
        String name = textInputLayoutName.getEditText().getText().toString().trim();
        String contact = textInputLayoutContact.getEditText().getText().toString().trim();
        String city = textInputLayoutCity.getEditText().getText().toString().trim();


        if (validateEmail(email)) {
            textInputLayoutEmail.setErrorEnabled(false);
            textInputLayoutEmail.setError(null);
            flag = flag + 1;
        } else {
            textInputLayoutEmail.setError("Please Enter a Valid Email");
        }

        if (validateName(name)) {
            textInputLayoutName.setErrorEnabled(false);
            textInputLayoutName.setError(null);
            flag = flag + 1;
        } else {
            textInputLayoutName.setError("Please Enter a Valid Name");
        }

        if (validateContact(contact)) {
            textInputLayoutContact.setErrorEnabled(false);
            textInputLayoutContact.setError(null);
            flag = flag + 1;
        } else {
            textInputLayoutContact.setError("Please Enter a Valid Contact");
        }

        if(validatePassword(password)){
            textInputLayoutPassword.setErrorEnabled(false);
            textInputLayoutPassword.setError(null);
            flag = flag + 1;
        }else{
            textInputLayoutPassword.setError("Please Enter a Valid Password");
        }

        if(city.isEmpty()){
            textInputLayoutCity.setError("Please Select a City");
        }else{
            textInputLayoutCity.setErrorEnabled(false);
            textInputLayoutCity.setError(null);
            flag = flag+1;
        }

        //If all validation passes.
        if(flag == 5){
            Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            this.finish();
        }

    }

    //Validating email
    private boolean validateEmail(String email) {
        if (email.isEmpty() || email.length() > 50) {
            return false;
        } else {
            return isValidEmail(email);
        }
    }

    //Validating password
    private boolean validatePassword(String password) {
        if (password.isEmpty() || password.length() > 15) {
            return false;
        } else {
            return true;
        }
    }

    //Validating name
    private boolean validateName(String name) {
        if (name.isEmpty() || name.length() > 30) {
            return false;
        } else {
            return true;
        }
    }

    //Validating contact
    private boolean validateContact(String contact) {
        if (contact.isEmpty() || contact.length() != 10) {
            return false;
        } else {
            return true;
        }
    }

    //Validating email string
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.isEmpty()) {
            return false;
        } else {
            if (email.matches(emailPattern)) {
                return true;
            } else {
                return false;
            }
        }
    }

}
