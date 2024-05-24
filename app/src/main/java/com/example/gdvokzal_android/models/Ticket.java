package com.example.gdvokzal_android.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Ticket implements Parcelable {
    private String fromTown;
    private String destinationTown;
    private int numberOfPassengers;
    private String date;
    private String time;

    public Ticket(String fromTown, String destinationTown, int numberOfPassengers, String date, String time) {
        this.fromTown = fromTown;
        this.destinationTown = destinationTown;
        this.numberOfPassengers = numberOfPassengers;
        this.date = date;
        this.time = time;
    }

    protected Ticket(Parcel in) {
        fromTown = in.readString();
        destinationTown = in.readString();
        numberOfPassengers = in.readInt();
        date = in.readString();
        time = in.readString();
    }

    public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
        @Override
        public Ticket createFromParcel(Parcel in) {
            return new Ticket(in);
        }

        @Override
        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };

    public String getFromTown() {
        return fromTown;
    }

    public String getDestinationTown() {
        return destinationTown;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fromTown);
        parcel.writeString(destinationTown);
        parcel.writeInt(numberOfPassengers);
        parcel.writeString(date);
        parcel.writeString(time);
    }

    public double getPrice() {
        return 50 * numberOfPassengers;
    }


}