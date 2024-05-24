package com.example.gdvokzal_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gdvokzal_android.models.Ticket;

public class ResultActivity extends AppCompatActivity {

private TextView tvFrom;
    private TextView tvDestination;
    private TextView tvNumberOfPassengers;
    private TextView tvDate;
    private TextView tvTime;
    private TextView tvPrice;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        findViews();

        Ticket ticket = getIntent().getParcelableExtra("ticket");

        tvFrom.setText("From: " + ticket.getFromTown());
        tvDestination.setText("Destination: " + ticket.getDestinationTown());
        tvNumberOfPassengers.setText("Number of passengers: " + ticket.getNumberOfPassengers());
        tvDate.setText("Date: " + ticket.getDate());
        tvTime.setText("Time: " + ticket.getTime());
        tvPrice.setText("Price: " + ticket.getPrice() + "$");

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findViews()
    {
         tvFrom = findViewById(R.id.tv_from);
         tvDestination = findViewById(R.id.tv_destination);
         tvNumberOfPassengers = findViewById(R.id.tv_number_of_passengers);
         tvDate = findViewById(R.id.tv_date);
         tvTime = findViewById(R.id.tv_time);
            tvPrice = findViewById(R.id.tv_price);

    }
}