package com.mc.carrent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class Register extends AppCompatActivity {

    private Toolbar toolbar;
    private AutoCompleteTextView autoCompleteTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        toolbar = findViewById(R.id.toolbarRegister);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextViewRegisterCity);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String[] cities = new String[]{"Halifax", "Dartmouth", "City 3", "City 4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.dropdown_city, cities);

        autoCompleteTextView.setAdapter(adapter);
    }
}
