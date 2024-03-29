package com.mc.carrent.activities;

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
import com.mc.carrent.R;
import com.mc.carrent.SingletonRequest;
import com.mc.carrent.models.Url;
import com.mc.carrent.adapters.CurrentBookingRecyclerViewAdapter;
import com.mc.carrent.adapters.PreviousBookingRecyclerViewAdapter;
import com.mc.carrent.models.Car;
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
    private ArrayList<Car> carCurrentArrayList, previousCarList;
    private Car car = null;
    private String to = null, from = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);
        //Fetch the details from the intent
        from = getIntent().getStringExtra("from");
        to = getIntent().getStringExtra("to");
        car = (Car) getIntent().getSerializableExtra("car");

        progressBar = findViewById(R.id.progressBarBooking);

        carCurrentArrayList = new ArrayList<>();
        previousCarList = new ArrayList<>();

        toolbar = findViewById(R.id.toolbarBooking);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        currentBookingRecyclerView = findViewById(R.id.recyclerViewCurrentBooking);
        previousBookingRecyclerView = findViewById(R.id.recyclerViewPreviousBooking);

        if(car != null) {
            carCurrentArrayList.add(car);
        }
        currentBookingRecyclerViewAdapter = new CurrentBookingRecyclerViewAdapter(this,carCurrentArrayList);
        currentBookingRecyclerView.setAdapter(currentBookingRecyclerViewAdapter);
        currentBookingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        previousCarList = getCarList();
        previousBookingRecyclerViewAdapter = new PreviousBookingRecyclerViewAdapter(this,previousCarList);
        previousBookingRecyclerView.setAdapter(previousBookingRecyclerViewAdapter);
        previousBookingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private ArrayList<Car> getCarList() {
        //Will fetch the user id from the sharedpreferences.
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("LoginActivity", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId",null);
        String url = Url.bookingUrl+userId;

        //Will fetch a jsonarray request.
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
                    previousCarList.add(car);

                }
                //After fetching the data updating the recyclerview.
                previousBookingRecyclerViewAdapter.setData(previousCarList);

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


        return previousCarList;

    }
    //To allow the user to rate the vehicle
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

    // To allow the user to remove the booked vehicle
    public void removeBooking(int position){
        this.carCurrentArrayList.remove(position);
        currentBookingRecyclerViewAdapter.setData(carCurrentArrayList);
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int i, @NotNull String s) {
        Car car = previousCarList.get(carPosition);
        previousCarList.remove(carPosition);
        car.setRated(true);
        previousCarList.add(carPosition,car);

        previousBookingRecyclerViewAdapter.setData(previousCarList);

        Toast.makeText(this, "You rated Car: "+ i, Toast.LENGTH_SHORT).show();
    }
}
