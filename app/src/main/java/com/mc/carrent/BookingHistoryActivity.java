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
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookingHistoryActivity extends AppCompatActivity implements RatingDialogListener {

    private RecyclerView currentBookingRecyclerView, previousBookingRecyclerView;
    private CurrentBookingRecyclerViewAdapter currentBookingRecyclerViewAdapter;
    private PreviousBookingRecyclerViewAdapter previousBookingRecyclerViewAdapter;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private int carPosition = 0;
    private ArrayList<Car> carArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        progressBar = findViewById(R.id.progressBarBooking);

        carArrayList = new ArrayList<>();

        toolbar = findViewById(R.id.toolbarBooking);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        currentBookingRecyclerView = findViewById(R.id.recyclerViewCurrentBooking);
        previousBookingRecyclerView = findViewById(R.id.recyclerViewPreviousBooking);

        ArrayList<Car> carArrayList = getCarList();
        currentBookingRecyclerViewAdapter = new CurrentBookingRecyclerViewAdapter(this,carArrayList);
        currentBookingRecyclerView.setAdapter(currentBookingRecyclerViewAdapter);
        currentBookingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        previousBookingRecyclerViewAdapter = new PreviousBookingRecyclerViewAdapter(this,carArrayList);
        previousBookingRecyclerView.setAdapter(previousBookingRecyclerViewAdapter);
        previousBookingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private ArrayList<Car> getCarList() {
//        Car car = new Car("Model 1", 4.5, 23, 44.647491, -63.576211);
//        Car car1 = new Car("Model 2", 4.5, 25, 44.649093, -63.573144);
//        Car car2 = new Car("Model 3", 3.5, 33, 44.649460, -63.578699);
//        Car car3 = new Car("Model 4", 2.5, 44, 44.640613, -63.582478);
//        carArrayList.add(car);
//        carArrayList.add(car1);
//        carArrayList.add(car2);
//        carArrayList.add(car3);


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);

        String userId = sharedPreferences.getString("userId",null);

        String url = Url.bookingUrl+userId;

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
                previousBookingRecyclerViewAdapter.setData(carArrayList);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError || error instanceof NoConnectionError) {
                    Toast.makeText(BookingHistoryActivity.this, "Please Check your Internet Connection!!!", Toast.LENGTH_SHORT).show();
                } else {
                    String statuscode = String.valueOf(error.networkResponse.statusCode);
                    Toast.makeText(BookingHistoryActivity.this, "Some error", Toast.LENGTH_SHORT).show();
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

    public void setRating(int position){
        this.carPosition = position;
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setTitle("Rate the Car")
                .setNumberOfStars(5)
                .setDefaultRating(3)
                .setStarColor(R.color.colorAccent)
                .setCommentInputEnabled(false)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .create(this)
                .show();
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int i, @NotNull String s) {
        Toast.makeText(this, "You rated :"+i+" Car id: "+carArrayList.get(carPosition).getId(), Toast.LENGTH_SHORT).show();
    }
}
