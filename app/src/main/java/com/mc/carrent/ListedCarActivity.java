package com.mc.carrent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListedCarActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ListedCarRecyclerViewAdapter recyclerViewAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listed_car);

        progressBar = findViewById(R.id.progressBarListedCar);
        toolbar = findViewById(R.id.toolbarListedCar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.recyclerViewListedCar);
        ArrayList<Car> carArrayList = getCarList();
        recyclerViewAdapter = new ListedCarRecyclerViewAdapter(this, carArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private ArrayList<Car> getCarList() {
        ArrayList<Car> carArrayList = new ArrayList<>();
//        Car car = new Car("Model 1", 4.5, 23, 44.647491, -63.576211);
//        Car car1 = new Car("Model 2", 4.5, 25, 44.649093, -63.573144);
//        Car car2 = new Car("Model 3", 3.5, 33, 44.649460, -63.578699);
//        Car car3 = new Car("Model 4", 2.5, 44, 44.640613, -63.582478);
//        carArrayList.add(car);
//        carArrayList.add(car1);
//        carArrayList.add(car2);
//        carArrayList.add(car3);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);

        String userId = sharedPreferences.getString("userId", null);

        String url = Url.bookingUrl + userId;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.INVISIBLE);
                JSONObject object = new JSONObject();
                int id = 0;
                String carModel = "", carDescription = "", imageUrl = "";
                double price = 0, latitude = 0, longitude = 0;
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

                    Car car = new Car(id, carModel, 4.5, price, latitude, longitude, imageUrl, carDescription);
                    carArrayList.add(car);

                }
                recyclerViewAdapter.setData(carArrayList);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError || error instanceof NoConnectionError) {
                    Toast.makeText(ListedCarActivity.this, "Please Check your Internet Connection!!!", Toast.LENGTH_SHORT).show();
                } else {
                    String statuscode = String.valueOf(error.networkResponse.statusCode);
                    Toast.makeText(ListedCarActivity.this, "Some error", Toast.LENGTH_SHORT).show();
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

}
