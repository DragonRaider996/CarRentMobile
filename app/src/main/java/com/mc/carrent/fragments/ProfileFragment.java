package com.mc.carrent.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mc.carrent.activities.BookingHistoryActivity;
import com.mc.carrent.activities.HelpActivity;
import com.mc.carrent.activities.ListedCarActivity;
import com.mc.carrent.activities.LoginActivity;
import com.mc.carrent.R;
import com.mc.carrent.activities.RentCarActivity;

public class ProfileFragment extends Fragment {

    private TextView rentACar, bookingHistory, listedCar, help, logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        rentACar = view.findViewById(R.id.textViewProfileRent);
        bookingHistory = view.findViewById(R.id.textViewProfileBooking);
        listedCar = view.findViewById(R.id.textViewProfileListedCar);
        help = view.findViewById(R.id.textViewProfileHelp);
        logout = view.findViewById(R.id.textViewProfileLogout);
        rentACar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RentCarActivity.class);
                startActivity(intent);

            }
        });

        //handling booking history click
        bookingHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookingHistoryActivity.class);
                startActivity(intent);
            }
        });

        //handling listed car click
        listedCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListedCarActivity.class);
                startActivity(intent);
            }
        });

        //handling help click
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                startActivity(intent);
            }
        });

        //handling logout click
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginActivity", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("userId");
                editor.remove("email");
                editor.apply();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
}
