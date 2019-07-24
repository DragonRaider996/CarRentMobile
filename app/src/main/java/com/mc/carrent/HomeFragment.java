package com.mc.carrent;

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
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    private AutoCompleteTextView autoCompleteTextView;
    private TextInputLayout textInputLayout;
    private TextView textViewFrom, textViewTo;
    private DatePickerDialog datePickerDialogFrom, datePickerDialogTo;
    private MaterialButton button;
    private int fromDate = 0, fromMonth = 0, fromYear = 0, toDate = 0, toMonth = 0, toYear = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        textInputLayout = view.findViewById(R.id.textInputCityDropdown);
        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextViewCity);
        textViewFrom = view.findViewById(R.id.textViewFrom);
        textViewTo = view.findViewById(R.id.textViewTo);
        button = view.findViewById(R.id.buttonHome);

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
                Toast.makeText(getActivity(), String.valueOf(Calendar.DAY_OF_MONTH), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < 30; i++) {
                    Calendar day = Calendar.getInstance();
                    day.add(Calendar.DAY_OF_MONTH, i);
                    days[i] = day;
                }
                datePickerDialogFrom.setSelectableDays(days);
                datePickerDialogFrom.show(requireFragmentManager(), "Datepickerdialog");
            }
        });

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
                    Toast.makeText(getActivity(), String.valueOf(Calendar.DAY_OF_MONTH), Toast.LENGTH_SHORT).show();
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                String city = textInputLayout.getEditText().getText().toString().trim();
                if (city.isEmpty()) {
                    textInputLayout.setError("Please select City");
                } else {
                    textInputLayout.setErrorEnabled(false);
                    textInputLayout.setError(null);
                    flag = flag + 1;
                }

                if(fromDate == 0 || fromMonth == 0 || fromYear == 0){
                    Toast.makeText(getActivity(), "Please select from date", Toast.LENGTH_SHORT).show();
                }else{
                    flag = flag + 1;
                }

                if(toDate == 0 || toMonth == 0 || toYear == 0){
                    Toast.makeText(getActivity(), "Please select to date", Toast.LENGTH_SHORT).show();
                }else{
                    flag = flag + 1;
                }

                Calendar fromCalendar = Calendar.getInstance();
                fromCalendar.set(fromYear, fromMonth, fromDate);

                Calendar toCalendar = Calendar.getInstance();
                toCalendar.set(toYear, toMonth, toDate);

                //long daysDiff = ChronoUnit.DAYS.between(fromCalendar.toInstant(),toCalendar.toInstant());

                if (flag == 3) {
                    Intent intent = new Intent(getContext(), CarResultActivity.class);
                    startActivity(intent);
                }
            }
        });

        String[] cities = new String[]{"Halifax", "Dartmouth", "City 3", "City 4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.dropdown_city, cities);

        autoCompleteTextView.setAdapter(adapter);
        return view;

    }
}
