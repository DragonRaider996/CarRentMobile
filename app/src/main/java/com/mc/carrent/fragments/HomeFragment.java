package com.mc.carrent.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.mc.carrent.activities.CarResultActivity;
import com.mc.carrent.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    private AutoCompleteTextView autoCompleteTextViewCity, autoCompleteTextViewVehicle;
    private TextInputLayout textInputLayoutCity, textInputLayoutVehicle;
    private TextView textViewFrom, textViewTo;
    private DatePickerDialog datePickerDialogFrom, datePickerDialogTo;
    private MaterialButton button;
    private int fromDate = 0, fromMonth = 0, fromYear = 0, toDate = 0, toMonth = 0, toYear = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        textInputLayoutCity = view.findViewById(R.id.textInputCityDropdown);
        textInputLayoutVehicle = view.findViewById(R.id.textInputVehicleDropdown);
        autoCompleteTextViewCity = view.findViewById(R.id.autoCompleteTextViewCity);
        autoCompleteTextViewVehicle = view.findViewById(R.id.autoCompleteTextViewVehicle);
        textViewFrom = view.findViewById(R.id.textViewFrom);
        textViewTo = view.findViewById(R.id.textViewTo);
        button = view.findViewById(R.id.buttonHome);

        //Handling from click
        textViewFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                datePickerDialogFrom = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                fromDate = dayOfMonth;
                                fromMonth = monthOfYear;
                                fromYear = year;
                                textViewFrom.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear) + "/" + String.valueOf(year));
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DATE)
                );
                Calendar[] days = new Calendar[30];
                for (int i = 0; i < 30; i++) {
                    Calendar day = Calendar.getInstance();
                    day.add(Calendar.DAY_OF_MONTH, i);
                    days[i] = day;
                }
                datePickerDialogFrom.setSelectableDays(days);
                datePickerDialogFrom.show(requireFragmentManager(), "Datepickerdialog");
            }
        });

        //handling to click
        textViewTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromDate == 0 || fromMonth == 0 || fromYear == 0) {
                    Toast.makeText(getActivity(), "Please Select From Date First", Toast.LENGTH_SHORT).show();
                } else {
                    Calendar calendar = Calendar.getInstance();
                    datePickerDialogTo = DatePickerDialog.newInstance(
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                    toDate = dayOfMonth;
                                    toMonth = monthOfYear;
                                    toYear = year;
                                    textViewTo.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear) + "/" + String.valueOf(year));
                                }
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DATE)
                    );
                    Calendar[] days = new Calendar[30];
                    for (int i = 0; i < 30; i++) {
                        Calendar startDay = Calendar.getInstance();
                        startDay.set(fromYear, fromMonth, fromDate, 0, 0);
                        startDay.add(Calendar.DATE, i);
                        days[i] = startDay;
                    }
                    datePickerDialogTo.setSelectableDays(days);
                    datePickerDialogTo.show(requireFragmentManager(), "DatePickerDialog");
                }
            }
        });

        //handling search click
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                String city = textInputLayoutCity.getEditText().getText().toString().trim();
                String vehicle = textInputLayoutVehicle.getEditText().getText().toString().trim();

                if (city.isEmpty()) {
                    textInputLayoutCity.setError("Please select City");
                } else {
                    textInputLayoutCity.setErrorEnabled(false);
                    textInputLayoutCity.setError(null);
                    flag = flag + 1;
                }

                if(vehicle.isEmpty()){
                    textInputLayoutVehicle.setError("Please select Vehicle");
                }else{
                    textInputLayoutVehicle.setErrorEnabled(false);
                    textInputLayoutVehicle.setError(null);
                    flag = flag + 1;
                }

                if (fromDate == 0 || fromMonth == 0 || fromYear == 0) {
                    Toast.makeText(getActivity(), "Please select from date", Toast.LENGTH_SHORT).show();
                } else {
                    flag = flag + 1;
                }

                if (toDate == 0 || toMonth == 0 || toYear == 0) {
                    Toast.makeText(getActivity(), "Please select to date", Toast.LENGTH_SHORT).show();
                } else {
                    flag = flag + 1;
                }

                Calendar fromCalendar = Calendar.getInstance();
                fromCalendar.set(fromYear, fromMonth, fromDate);

                Calendar toCalendar = Calendar.getInstance();
                toCalendar.set(toYear, toMonth, toDate);

                //if validated
                if (flag == 4) {
                    long remainingDays = Math.round((float) (toCalendar.getTimeInMillis() - fromCalendar.getTimeInMillis()) / (24 * 60 * 60 * 1000));
                    //check if from date is before to date.
                    if (remainingDays < 0) {
                        Toast.makeText(getActivity(), "Please select proper dates", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getContext(), CarResultActivity.class);
                        String from = fromDate + "/" + fromMonth + "/" + fromYear;
                        String to = toDate + "/" + toMonth + "/" + toYear;
                        intent.putExtra("vehicle",vehicle);
                        intent.putExtra("from", from);
                        intent.putExtra("to", to);
                        startActivity(intent);
                    }
                }
            }
        });

        //setting the cities
        String[] cities = new String[]{"Halifax", "Dartmouth", "Sydney", "Truro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.dropdown_city, cities);

        //setting the vehicles
        String[] vehicles = new String[] {"Car", "Boat", "Motor Bike"};
        ArrayAdapter<String> vehicleAdapter = new ArrayAdapter<String>(getContext(), R.layout.dropdown_city, vehicles);
        autoCompleteTextViewVehicle.setAdapter(vehicleAdapter);
        autoCompleteTextViewCity.setAdapter(adapter);
        return view;

    }
}
