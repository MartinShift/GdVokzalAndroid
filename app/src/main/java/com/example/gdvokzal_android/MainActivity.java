package com.example.gdvokzal_android;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gdvokzal_android.models.CustomArrayAdapter;
import com.example.gdvokzal_android.models.Ticket;

public class MainActivity extends AppCompatActivity {

    private List<String> towns;
    private List<String> times;
    Spinner spinnerTown;
    Spinner spinnerDestinationTown;
    Spinner spinnerTime;
    Button btnDate;
    Button btnIncrease;
    Button btnDecrease;
    TextView tvNumberOfPlaces;
    private boolean userIsInteracting;
    Button btnFlip;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        towns = generateTowns();
        times = generateTimes();

        findViews();
        setupSpinners();
        setupListeners();
    }
    private boolean validateInput(String fromTown, String destinationTown, String date, String time) {
        if (fromTown.equals(destinationTown)) {
            Toast.makeText(this, "From town and destination town cannot be the same", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (date.equals("Select date")) {
            Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
            return false;
        }

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        try {
            Date selectedDateTime = format.parse(date + " " + time);
            Date currentDateTime = new Date();
            if (selectedDateTime.before(currentDateTime)) {
                Toast.makeText(this, "Date and time cannot be before the current date and time", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return true;
    }

    private void findViews() {
        spinnerTown = findViewById(R.id.spinner_town);
        spinnerDestinationTown = findViewById(R.id.spinner_destination_town);
        spinnerTime = findViewById(R.id.spinner_time);
        btnDate = findViewById(R.id.btn_date);
        btnIncrease = findViewById(R.id.btn_increase);
        btnDecrease = findViewById(R.id.btn_decrease);
        tvNumberOfPlaces = findViewById(R.id.tv_number_of_places);
        btnFlip = findViewById(R.id.btn_flip);
        btnSubmit = findViewById(R.id.btn_submit);
    }

    private void setupSpinners() {
        ArrayAdapter<String> townAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, towns);
        townAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTown.setAdapter(townAdapter);
        spinnerDestinationTown.setAdapter(townAdapter);

        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, times);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(timeAdapter);
    }

    private void setupListeners()
    {
        CustomArrayAdapter townAdapter = new CustomArrayAdapter(this, android.R.layout.simple_spinner_item, towns);
        townAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTown.setAdapter(townAdapter);
        spinnerDestinationTown.setAdapter(townAdapter);

        spinnerTown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userIsInteracting) {
                    ((CustomArrayAdapter) spinnerDestinationTown.getAdapter()).setDisabledPosition(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerDestinationTown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userIsInteracting) {
                    ((CustomArrayAdapter) spinnerTown.getAdapter()).setDisabledPosition(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                String strDate = format.format(calendar.getTime());
                                btnDate.setText(strDate);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentNumber = Integer.parseInt(tvNumberOfPlaces.getText().toString());
                if (currentNumber > 1) {
                    tvNumberOfPlaces.setText(String.valueOf(currentNumber - 1));
                }
            }
        });

        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentNumber = Integer.parseInt(tvNumberOfPlaces.getText().toString());
                tvNumberOfPlaces.setText(String.valueOf(currentNumber + 1));
            }
        });

        btnFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int townPosition = spinnerTown.getSelectedItemPosition();
                int destinationTownPosition = spinnerDestinationTown.getSelectedItemPosition();

                spinnerTown.setSelection(destinationTownPosition);
                spinnerDestinationTown.setSelection(townPosition);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fromTown = spinnerTown.getSelectedItem().toString();
                String destinationTown = spinnerDestinationTown.getSelectedItem().toString();
                int numberOfPassengers = Integer.parseInt(tvNumberOfPlaces.getText().toString());
                String date = btnDate.getText().toString();
                String time = spinnerTime.getSelectedItem().toString();
                if (!validateInput(fromTown, destinationTown, date, time)) {
                    return;
                }
                Ticket ticket = new Ticket(fromTown, destinationTown, numberOfPassengers, date, time);

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("ticket", ticket);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        userIsInteracting = true;
    }

    private List<String> generateTowns() {
        List<String> towns = new ArrayList<>();
        towns.add("Kyiv");
        towns.add("Kharkiv");
        towns.add("Odesa");
        towns.add("Dnipro");
        towns.add("Donetsk");
        towns.add("Zaporizhia");
        towns.add("Lviv");
        towns.add("Kryvyi Rih");
        towns.add("Mykolaiv");
        towns.add("Mariupol");
        towns.add("Luhansk");
        towns.add("Vinnytsia");
        towns.add("Makiivka");
        towns.add("Sevastopol");
        towns.add("Simferopol");
        towns.add("Kherson");
        towns.add("Poltava");
        towns.add("Chernihiv");
        towns.add("Cherkasy");
        towns.add("Zhytomyr");
        towns.add("Sumy");
        towns.add("Horlivka");
        towns.add("Rivne");
        towns.add("Kirovohrad");
        towns.add("Ivano-Frankivsk");
        towns.add("Ternopil");
        towns.add("Lutsk");
        towns.add("Bila Tserkva");
        towns.add("Kremenchuk");
        towns.add("Kramatorsk");
        towns.add("Melitopol");
        towns.add("Kerch");
        towns.add("Nikopol");
        towns.add("Slavutych");
        towns.add("Berdiansk");
        towns.add("Uzhhorod");
        return towns;
    }

    private List<String> generateTimes() {
        List<String> times = new ArrayList<>();
        for (int hours = 0; hours < 24; hours++) {
            for (int minutes = 0; minutes < 60; minutes += 30) {
                times.add(String.format(Locale.getDefault(), "%02d:%02d", hours, minutes));
            }
        }
        return times;
    }
}