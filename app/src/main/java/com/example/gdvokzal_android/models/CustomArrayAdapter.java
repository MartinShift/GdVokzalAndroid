package com.example.gdvokzal_android.models;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<String> {
    private int disabledPosition;

    public CustomArrayAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
    }

    @Override
    public boolean isEnabled(int position) {
        return position != disabledPosition;
    }

    public void setDisabledPosition(int position) {
        disabledPosition = position;
    }
}
