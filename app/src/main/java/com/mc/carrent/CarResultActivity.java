package com.mc.carrent;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import de.mrapp.android.dialog.MaterialDialog;

public class CarResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CarResultRecyclerViewAdapter recyclerViewAdapter;
    private Toolbar toolbar;
    private int index = 0;
    private int MY_PERMISSION_ACCESS_COARSE_LOCATION = 100;
    private FusedLocationProviderClient fusedLocationClient;
    private double latitude,longitude;
    private ProgressBar progressBar;
    private String from = null,to = null;
    private ArrayList<Car> carArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_result);
        from = getIntent().getStringExtra("from");
        to = getIntent().getStringExtra("to");
        recyclerView = findViewById(R.id.recyclerViewCarResult);
        toolbar = findViewById(R.id.toolbarCarResult);
        progressBar = findViewById(R.id.progressBarCarResult);
        setSupportActionBar(toolbar);
        carArrayList = new ArrayList<>();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_ACCESS_COARSE_LOCATION
            );
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Toast.makeText(CarResultActivity.this, String.valueOf(latitude)+","+String.valueOf(longitude), Toast.LENGTH_SHORT).show();
            }
        });
        ArrayList<Car> carArrayList = getCarList();
        recyclerViewAdapter = new CarResultRecyclerViewAdapter(this, carArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSION_ACCESS_COARSE_LOCATION) {
            Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private ArrayList<Car> getCarList() {
//        Car car = new Car(1,"Model 1", 4.5, 23, 44.647491, -63.576211);
//        Car car1 = new Car(2,"Model 2", 4.5, 25, 44.649093, -63.573144);
//        Car car2 = new Car(3,"Model 3", 3.5, 33, 44.649460, -63.578699);
//        Car car3 = new Car(4,"Model 4", 2.5, 44, 44.640613, -63.582478);
//        carArrayList.add(car);
//        carArrayList.add(car1);
//        carArrayList.add(car2);
//        carArrayList.add(car3);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);

        String userId = sharedPreferences.getString("userId",null);

        String url = Url.fetchCarUrl+userId;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.INVISIBLE);
                JSONObject object = new JSONObject();
                int id = 0;
                String carModel="", carDescription="", imageUrl="";
                double price= 0, latitude = 0, longitude = 0;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        object = response.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        id = object.getInt("id");
                        carModel = object.getString("modelName");
                        carDescription = object.getString("modelDescription");
                        price = object.getDouble("price");
                        latitude = object.getDouble("latitude");
                        longitude = object.getDouble("longitude");
                        imageUrl = object.getString("imageUrl");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Car car = new Car(id,carModel,4.5,price,latitude,longitude,imageUrl,carDescription);
                    carArrayList.add(car);

                }
                recyclerViewAdapter.setData(carArrayList);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError || error instanceof NoConnectionError) {
                    Toast.makeText(CarResultActivity.this, "Please Check your Internet Connection!!!", Toast.LENGTH_SHORT).show();
                } else {
                    String statuscode = String.valueOf(error.networkResponse.statusCode);
                    Toast.makeText(CarResultActivity.this, "Some error", Toast.LENGTH_SHORT).show();
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

        return carArrayList;
    }

    public void bookNow(int position){
        Car car = carArrayList.get(position);
        Intent intent = new Intent(this,BookingHistoryActivity.class);
        intent.putExtra("car", (Serializable) car);
        intent.putExtra("from",from);
        intent.putExtra("to",to);
        startActivity(intent);
    }

    public void cardViewClick(int position){
        Car car = carArrayList.get(position);
        Intent intent = new Intent(this, CarDetailActivity.class);
        intent.putExtra("car", (Serializable) car);
        intent.putExtra("from",from);
        intent.putExtra("to",to);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.car_search_result, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.sort) {
            MaterialDialog dialog;
            MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
            builder.setTitle("Sort By");
            builder.setSingleChoiceItems(R.array.sort, index, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int position) {
                    sortData(position);
                    index = position;
                    dialog.dismiss();
                }
            });
            dialog = builder.show();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void sortData(int position){
        if(position == 0){
            Collections.sort(this.carArrayList, new Comparator<Car>() {
                @Override
                public int compare(Car o1, Car o2) {
                    if(o1.getPrice() > o2.getPrice()){
                        return 1;
                    }else{
                        return -1;
                    }
                }
            });
            recyclerViewAdapter.setData(carArrayList);
        }else if(position == 1){
            ArrayList<Integer> distances = getDistances();
            Collections.sort(this.carArrayList, new Comparator<Car>() {
                @Override
                public int compare(Car o1, Car o2) {
                    int index1 = carArrayList.indexOf(o1);
                    int index2 = carArrayList.indexOf(o2);
                    if(distances.get(index1) >= distances.get(index2)){
                        return -1;
                    }else{
                        return 1;
                    }
                }
            });
            recyclerViewAdapter.setData(carArrayList);
        }else{
            Collections.sort(this.carArrayList, new Comparator<Car>() {
                @Override
                public int compare(Car o1, Car o2) {
                    if(o1.getCarRating() > o2.getCarRating()){
                        return 1;
                    }else{
                        return -1;
                    }
                }
            });
            recyclerViewAdapter.setData(carArrayList);
        }
    }

    public ArrayList<Integer> getDistances(){
        ArrayList<Integer> distances = new ArrayList<>();
        for(int i=0; i<this.carArrayList.size();i++){
            Car car = this.carArrayList.get(i);
            double carLat = car.getLat();
            double carLong = car.getLng();

            double latDistance = Math.toRadians(latitude - carLat);
            double lngDistance = Math.toRadians(longitude - carLong);

            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(carLat))
                    * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            int distance = (int) (Math.round(6371000 * c));

            distances.add(distance);

        }
        return distances;
    }
}
