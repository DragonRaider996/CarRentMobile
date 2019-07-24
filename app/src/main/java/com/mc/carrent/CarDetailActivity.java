package com.mc.carrent;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class CarDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private CardView cardViewPhone, cardViewLocation;
    private ImageView imageView, imageViewMaps;
    private TextView textViewCarDescription;
    private int MY_CALL_PERMISSION = 85;
    private double latitude, longitude;
    private Car car = null;
    private String from = null,to = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        from = getIntent().getStringExtra("from");
        to = getIntent().getStringExtra("to");
        car = (Car) getIntent().getSerializableExtra("car");
        toolbar = findViewById(R.id.toolbarCarDetail);
        cardViewPhone = findViewById(R.id.cardViewContactOwner);
        cardViewLocation = findViewById(R.id.cardViewLocation);
        imageView = findViewById(R.id.imageCarDetail);
        imageViewMaps = findViewById(R.id.imageViewMaps);
        textViewCarDescription = findViewById(R.id.textViewCarDetailDescription);
        String url = car.getUrl();
        latitude = car.getLat();
        longitude = car.getLng();
        String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?zoom=15&size=600x300&maptype=roadmap&markers=color:blue|label:S|"+latitude+","+longitude+"&key=AIzaSyBloNDTRp1RYXiR5GSglDZ5ki0ypDbf-0o";
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();

        final ImageLoader imageLoader1 = SingletonRequest.getInstance(this.getApplicationContext()).getImageLoader();
        imageLoader1.get(imageUrl, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                imageViewMaps.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError || error instanceof NetworkError) {
                    Toast.makeText(CarDetailActivity.this, "Please Check your Internet Connection!!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CarDetailActivity.this, "Error in Image", Toast.LENGTH_SHORT).show();
                }
            }
        });


        final ImageLoader imageLoader = SingletonRequest.getInstance(this.getApplicationContext()).getImageLoader();
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                imageView.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError || error instanceof NetworkError) {
                    Toast.makeText(CarDetailActivity.this, "Please Check your Internet Connection!!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CarDetailActivity.this, "Error in Image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textViewCarDescription.setText(car.getCarDescription());

        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarCarDetail);
        setSupportActionBar(toolbar);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_CALL_PERMISSION
            );
        }
        toolbar.setTitle(car.getCarModel());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cardViewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long number = 9029895396L;
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + number));//change the number
                startActivity(intent);
            }
        });

        cardViewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+String.valueOf(latitude)+","+String.valueOf(longitude)));
                startActivity(intent);
                // AIzaSyBloNDTRp1RYXiR5GSglDZ5ki0ypDbf-0o
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_CALL_PERMISSION) {
            Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void bookNow(View view){
        Intent intent = new Intent(this,BookingHistoryActivity.class);
        intent.putExtra("car",car);
        intent.putExtra("from",from);
        intent.putExtra("to",to);
        startActivity(intent);
    }

}

