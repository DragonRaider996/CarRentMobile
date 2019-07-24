package com.mc.carrent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

        bookingHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookingHistoryActivity.class);
                startActivity(intent);
            }
        });

        listedCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListedCarActivity.class);
                startActivity(intent);
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
}
