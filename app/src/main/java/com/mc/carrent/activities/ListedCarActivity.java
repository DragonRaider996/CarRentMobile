package com.mc.carrent.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.mc.carrent.R;
import com.mc.carrent.SingletonRequest;
import com.mc.carrent.models.Url;
import com.mc.carrent.adapters.ListedCarRecyclerViewAdapter;
import com.mc.carrent.models.Car;

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
    private ArrayList<Car> carArrayList;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listed_car);

        textView = findViewById(R.id.textViewListedCar);
        textView.setVisibility(View.INVISIBLE);
        progressBar = findViewById(R.id.progressBarListedCar);
        toolbar = findViewById(R.id.toolbarListedCar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        carArrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerViewListedCar);
        carArrayList = getCarList();
        recyclerViewAdapter = new ListedCarRecyclerViewAdapter(this, carArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    //Will fetch the details from the server.
    private ArrayList<Car> getCarList() {

        //Fetching the user id stored.
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("LoginActivity", MODE_PRIVATE);
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

                if(carArrayList.size() == 0){
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("No Car have been listed by you");
                }

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

    //Removing the listed cars
    public void removeListedCar(int position){
        this.carArrayList.remove(position);
        recyclerViewAdapter.setData(carArrayList);
        if(this.carArrayList.size() == 0){
            textView.setVisibility(View.VISIBLE);
            textView.setText("No Car have been listed by you");
        }
    }

}
