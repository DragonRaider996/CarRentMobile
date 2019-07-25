package com.mc.carrent.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.mc.carrent.activities.BookingHistoryActivity;
import com.mc.carrent.models.Car;
import com.mc.carrent.R;
import com.mc.carrent.SingletonRequest;

import java.util.ArrayList;

public class PreviousBookingRecyclerViewAdapter extends RecyclerView.Adapter<PreviousBookingRecyclerViewAdapter.ViewHolder>{

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Car> carList;

    public PreviousBookingRecyclerViewAdapter(Context context, ArrayList<Car> carList) {
        this.context = context;
        this.carList = carList;
        layoutInflater = LayoutInflater.from(context);
    }

    //Setting the data to display in the recycler view
    public void setData(ArrayList<Car> carList){
        this.carList = carList;
        notifyItemRangeChanged(0,carList.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.single_row_car_result, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Car car = this.carList.get(position);
        String carPrice = "Price : " + car.getPrice() + "/day";
        String carRating = "Rating : " + car.getCarRating();
        String imageUrl = car.getUrl();
        boolean rated = false;
        //handling car rated or not
        rated = car.isRated();
        if(rated){
            holder.buttonCancel.setText("Vehicle Rated");
            holder.buttonCancel.setTextColor(ContextCompat.getColor(context,R.color.colorBlack));
            holder.buttonCancel.setEnabled(false);
        }else{
            holder.buttonCancel.setText("Rate the vehicle");
        }
        holder.textViewPrice.setText(carPrice);
        holder.textViewModel.setText(car.getCarModel());
        holder.textViewStar.setText(carRating);

        //setting image
        final ImageLoader imageLoader = SingletonRequest.getInstance(context.getApplicationContext()).getImageLoader();
        imageLoader.get(imageUrl, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.imageView.setImageBitmap(response.getBitmap());
            }
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError || error instanceof NetworkError) {
                    Toast.makeText(context, "Please Check your Internet Connection!!!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("Error",error.toString());
                    Toast.makeText(context, "Error in Image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //passing the button handling to the activity
        holder.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BookingHistoryActivity) context).setRating(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        protected ImageView imageView;
        protected TextView textViewModel, textViewStar, textViewPrice;
        protected Button buttonCancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewCarImage);
            textViewModel = itemView.findViewById(R.id.textViewCarModel);
            textViewStar = itemView.findViewById(R.id.textViewStar);
            textViewPrice = itemView.findViewById(R.id.textViewCarPrice);
            buttonCancel = itemView.findViewById(R.id.btnSingleRow);


        }
    }
}
