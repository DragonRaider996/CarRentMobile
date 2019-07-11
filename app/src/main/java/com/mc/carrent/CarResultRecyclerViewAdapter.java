package com.mc.carrent;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CarResultRecyclerViewAdapter extends RecyclerView.Adapter<CarResultRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Car> carList;

    public CarResultRecyclerViewAdapter(Context context, ArrayList<Car> carList){
        this.context = context;
        this.carList = carList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.single_row_car_result,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Car car = this.carList.get(position);
        String carPrice = "Price : "+car.getPrice()+"/day";
        String carRating = "Rating :"+car.getCarRating();
        holder.textViewPrice.setText(carPrice);
        holder.textViewModel.setText(car.getCarModel());
        holder.textViewStar.setText(carRating);
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        protected TextView textViewModel,textViewStar,textViewPrice;
        protected CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewModel = itemView.findViewById(R.id.textViewCarModel);
            textViewStar = itemView.findViewById(R.id.textViewStar);
            textViewPrice = itemView.findViewById(R.id.textViewCarPrice);
            cardView = itemView.findViewById(R.id.cardViewCarResult);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CarDetailActivity.class);
                    context.startActivity(intent);
                }
            });
        }

    }
}
