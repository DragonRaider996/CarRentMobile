package com.mc.carrent.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.mc.carrent.R;

public class RentCarActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextInputLayout textInputLayoutmodel, textInputLayoutDescription, textInputLayoutPrice, textInputLayoutVehicleType;
    private int MY_CAMERA_PERMISSION_CODE = 100;
    private int CAMERA_REQUEST = 150;
    private Button button;
    private Toolbar toolbar;
    private Bitmap carImage = null;
    private AutoCompleteTextView autoCompleteTextViewVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_car);
        imageView = findViewById(R.id.imageViewRentCar);
        textInputLayoutVehicleType = findViewById(R.id.textInputVehicleDropdownRent);
        autoCompleteTextViewVehicle = findViewById(R.id.autoCompleteTextViewVehicleRent);
        textInputLayoutmodel = findViewById(R.id.textInputLayoutCarModel);
        textInputLayoutDescription = findViewById(R.id.textInputLayoutCarDescription);
        textInputLayoutPrice = findViewById(R.id.textInputLayoutCarPrice);
        button = findViewById(R.id.buttonCarPic);

        Drawable icon = getDrawable(R.drawable.ic_camera_alt_black_24dp);
        button.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
        toolbar = findViewById(R.id.toolbarRentCar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Setting drop down values.
        String[] vehicles = new String[] {"Car", "Boat", "Motor Bike"};
        ArrayAdapter<String> vehicleAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_city, vehicles);
        autoCompleteTextViewVehicle.setAdapter(vehicleAdapter);

    }

    //Handling picture click
    public void clickPictures(View view) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA},
                    MY_CAMERA_PERMISSION_CODE);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        } else {
            Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
        }

    }

    //Stroing data when photo is clicked
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            carImage = photo;
            imageView.setImageBitmap(photo);
        }
    }


    //Handling submit click
    public void onSubmit(View view) {
        int flag = 0;
        String model = textInputLayoutmodel.getEditText().getText().toString().trim();
        String description = textInputLayoutDescription.getEditText().getText().toString().trim();
        String price = textInputLayoutPrice.getEditText().getText().toString().trim();
        String vehicle = textInputLayoutVehicleType.getEditText().getText().toString();

        if(vehicle.isEmpty()){
            textInputLayoutVehicleType.setError("Please Select a Valid Vehicle");
        }else{
            textInputLayoutVehicleType.setErrorEnabled(false);
            textInputLayoutVehicleType.setError(null);
            flag = flag + 1;
        }

        if (validateModel(model)) {
            textInputLayoutmodel.setErrorEnabled(false);
            textInputLayoutmodel.setError(null);
            flag = flag + 1;
        } else {
            textInputLayoutmodel.setError("Please Enter a Valid Model");
        }

        if (validateDescription(description)) {
            textInputLayoutDescription.setErrorEnabled(false);
            textInputLayoutDescription.setError(null);
            flag = flag + 1;
        } else {
            textInputLayoutDescription.setError("Please Enter a Valid Description");
        }

        if(validatePrice(price)){
            textInputLayoutPrice.setErrorEnabled(false);
            textInputLayoutPrice.setError(null);
            flag = flag + 1;
        } else {
            textInputLayoutPrice.setError("Please Enter a Valid Price");
        }

        if(carImage == null){
            Toast.makeText(this, "Please click an image", Toast.LENGTH_SHORT).show();
        }else{
            flag = flag + 1;
        }

        //Once all the validations are complete proceed
        if(flag == 5){
            Toast.makeText(this, "Vehicle has been listed", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,ListedCarActivity.class);
            startActivity(intent);
            finish();
        }

    }

    //Validating the model
    public boolean validateModel(String model) {
        if (model.isEmpty() || model.length() > 50) {
            return false;
        } else {
            return true;
        }
    }

    //Validating the description
    public boolean validateDescription(String description) {
        if (description.isEmpty() || description.length() > 250) {
            return false;
        } else {
            return true;
        }
    }

    //Validating the price
    public boolean validatePrice(String price) {
        if (price.isEmpty() || price.length() > 5) {
            return false;
        } else {
            return true;
        }
    }


}

